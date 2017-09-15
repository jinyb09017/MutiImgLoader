package com.abbott.mutiimgloader.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.abbott.mutiimgloader.R;
import com.abbott.mutiimgloader.cache.DiskLruCache;
import com.abbott.mutiimgloader.cache.LruCache;
import com.abbott.mutiimgloader.call.MergeCallBack;
import com.abbott.mutiimgloader.entity.Result;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * @author jyb jyb_96@sina.com on 2017/9/8.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class JImageLoader {

    private static final int MESSAGE_SEND_RESULT = 100;
    private static final int IMG_URL = R.drawable.ic_launcher;//只是一个点位id,没有其它作用。

    private static final int CPU_COUNT = 4;
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1; //corePoolSize为CPU数加1
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1; //maxPoolSize为2倍的CPU数加1
    private static final long KEEP_ALIVE = 5L; //存活时间为5s

    public static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 30;
    private static final int BUF_SIZE = 1024 * 8;
    private Context mContext;
    private int defaultId = R.drawable.ic_launcher_round;


    //获取当前进程的可用内存（单位KB）

    LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;
    private String Tag = "ImageLoader";

    public JImageLoader(Context context) {
        mContext = context.getApplicationContext();
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            protected int sizeof(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
        File diskCacheDir = getAppCacheDir(mContext, "images");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE) {
            //剩余空间大于我们指定的磁盘缓存大小
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void configDefaultPic(int defaultPic) {
        this.defaultId = defaultId;
    }

    public static File getAppCacheDir(Context context, String dirName) {
        String cacheDirString;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheDirString = context.getExternalCacheDir().getPath();
        } else {
            cacheDirString = context.getCacheDir().getPath();
        }

        return new File(cacheDirString + File.separator + dirName);
    }

    //主线程加载图片
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Result result = (Result) msg.obj;
            ImageView imageView = result.imageView;
            if (imageView != null) {


                String url = (String) imageView.getTag(IMG_URL);
                if (url.equals(result.url)) {
                    imageView.setImageBitmap(result.bitmap);
                } else {
                    Log.w(TAG, "The url associated with imageView has changed");
                }
            }


        }


    };


    /**
     * 异步加载
     *
     * @param url
     * @param imageView
     * @param dstWidth
     * @param dstHeight
     */
    public void displayImage(final String url, final ImageView imageView, final int dstWidth, final int dstHeight) {
        imageView.setTag(IMG_URL, url);
        Bitmap bitmap = loadFromMemory(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        //开启一个新的线程
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitMap(url, dstWidth, dstHeight);
                if (bitmap != null) {
                    Result result = new Result(bitmap, url, imageView);
                    Message msg = mMainHandler.obtainMessage(MESSAGE_SEND_RESULT, result);
                    msg.sendToTarget();

                }
            }
        };

        threadPoolExecutor.execute(loadBitmapTask);
    }

    /**
     * 默认按100 100的方法来存储
     *
     * @param urls
     * @param imageView
     * @param mergeCallBack
     */
    public void displayImages(final List<String> urls, final ImageView imageView, final MergeCallBack mergeCallBack) {
        displayImages(urls, imageView, mergeCallBack, 200, 200);
    }

    /**
     * @param urls
     * @param imageView
     * @param mergeCallBack
     * @param dstWidth      用于单个图像的压缩存储
     * @param dstHeight
     */
    public void displayImages(final List<String> urls, final ImageView imageView, final MergeCallBack mergeCallBack, final int dstWidth, final int dstHeight) {
        if (urls == null || urls.size() <= 0) {
            throw new IllegalArgumentException("url不能为空");
        }

        if (mergeCallBack == null) {
            throw new IllegalArgumentException("mergeCallBack 不能为空");
        }
        final String url = getNewUrlByList(urls, mergeCallBack.getMark());
        imageView.setTag(IMG_URL, url);

        Bitmap bitmap = loadFromMemory(url);
        if (bitmap != null) {
            Log.e(Tag, "displayImages this is from Memory");
            imageView.setImageBitmap(bitmap);
            return;
        }

        try {
            bitmap = loadFromDiskCache(url, dstWidth, dstHeight);
            if (bitmap != null) {
                Log.e(Tag, "displayImages this is from Disk");
                imageView.setImageBitmap(bitmap);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置一张默认图
        bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher_round);
        imageView.setImageBitmap(bitmap);

        Log.e(Tag, "displayImages this is from default");


        //开启一个新的线程，同步加载所有的图片。如果加载成功，则返回。
        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                ArrayList<Bitmap> bitmaps = loadBitMaps(urls, dstWidth, dstHeight);
                if (bitmaps != null && bitmaps.size() > 0) {
                    Result result;
                    if (mergeCallBack != null) {
                        Bitmap mergeBitmap = mergeCallBack.merge(bitmaps, mContext, imageView);

                        //加入缓存
                        try {
                            saveDru(url, mergeBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.e(Tag, "displayImages this is from Merge");
                        result = new Result(mergeBitmap, url, imageView);

                    } else {
                        result = new Result(bitmaps.get(0), url, imageView);
                    }

                    Message msg = mMainHandler.obtainMessage(MESSAGE_SEND_RESULT, result);
                    msg.sendToTarget();


                }
            }
        };

        threadPoolExecutor.execute(loadBitmapTask);
    }

    /**
     * 根据数组构造新的url.这里需要区别为1的时候，不进行合并操作，导致直接出现原图。所以需要调整下规则。
     *
     * @param urls
     * @return
     */
    public String getNewUrlByList(List<String> urls, String mark) {
        StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(url + mark);
        }

        return sb.toString();
    }


    private String getKeyFromUrl(String url) {
        String key;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            byte[] m = messageDigest.digest();
            return getString(m);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            key = String.valueOf(url.hashCode());
        }

        return key;

    }

    private static String getString(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            sb.append(b[i]);
        }

        return sb.toString();
    }


    /**
     * 同步加载
     *
     * @param url
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    private Bitmap loadBitMap(String url, int dstWidth, int dstHeight) {
        Bitmap bitmap = loadFromMemory(url);
        if (bitmap != null) {
            Log.e(Tag, "this is from Memory");
            return bitmap;
        }

        try {
            bitmap = loadFromDiskCache(url, dstWidth, dstHeight);
            if (bitmap != null) {
                Log.e(Tag, "this is from Disk");
                return bitmap;
            }

            bitmap = loadFromNet(url, dstWidth, dstHeight);
            Log.e(Tag, "this is from Net");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    private ArrayList<Bitmap> loadBitMaps(List<String> urls, int dstWidth, int dstHeight) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        for (String url : urls) {
            Bitmap bitmap = loadBitMap(url, dstWidth, dstHeight);
            if (bitmap != null) {
                bitmaps.add(bitmap);
            }

        }

        return bitmaps;
    }


    /**
     * 1、先加载到diskDruCache中
     * 2、再从diskDru中取出
     *
     * @param url
     * @param dstWidth
     * @param dstHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadFromNet(String url, int dstWidth, int dstHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("Do not load Bitmap in main thread.");
        }

        if (mDiskLruCache == null) {
            return null;
        }

        String key = getKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (getStreamFromUrl(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadFromDiskCache(url, dstWidth, dstHeight);
    }


    /**
     * 将合并的图像更新到指定的索引中
     *
     * @param url
     * @param bitmap
     * @throws IOException
     */
    public void saveDru(String url, Bitmap bitmap) throws IOException {

        if (mDiskLruCache == null) {
            return;
        }
        String key = getKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }


    }

    /**
     * 从磁盘加载
     *
     * @param url
     * @param dstWidth
     * @param dstHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadFromDiskCache(String url, int dstWidth, int dstHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.w("warn", "should not load bitmap in main thread");
        }

        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = getKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(0);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            bitmap = decodeSampledBitmapFromFD(fileDescriptor, dstWidth, dstHeight);
            if (bitmap != null) {
                //加入缓存队列中
                addToMemoryCache(key, bitmap);
            }
        }
        return bitmap;

    }

    /**
     * 添加到内存缓存
     *
     * @param key
     * @param bitmap
     */
    private void addToMemoryCache(String key, Bitmap bitmap) {
        if (getFromMemoryCache(key) == null) {
            //不存在时才添加
            mMemoryCache.put(key, bitmap);
        }
    }


    private Bitmap getFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }


    private Bitmap decodeSampledBitmapFromFD(FileDescriptor fileDescriptor, int dstWidth, int dstHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        //calInSampleSize方法的实现请见“Android开发之高效加载Bitmap”这篇博文
        options.inSampleSize = calSampleSize(options, dstWidth, dstHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

    }


    public static int calSampleSize(BitmapFactory.Options options, int dstWidth, int dstHeight) {
        int rawWidth = options.outWidth;
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        if (rawWidth > dstWidth || rawHeight > dstHeight) {
            float ratioHeight = (float) rawHeight / dstHeight;
            float ratioWidth = (float) rawWidth / dstHeight;
            inSampleSize = (int) Math.min(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }

    private boolean getStreamFromUrl(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream(), BUF_SIZE);
            bos = new BufferedOutputStream(outputStream);

            int byteRead;
            while ((byteRead = bis.read()) != -1) {
                bos.write(byteRead);
            }

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            HttpUtils.close(bis);
            HttpUtils.close(bos);
        }

        return false;
    }


    private Bitmap loadFromMemory(String url) {
        Log.e(Tag, "this is from memory:key=" + getKeyFromUrl(url));
        return mMemoryCache.get(getKeyFromUrl(url));
    }


}
