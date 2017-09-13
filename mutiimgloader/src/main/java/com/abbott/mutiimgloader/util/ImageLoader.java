package com.abbott.mutiimgloader.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.abbott.mutiimgloader.cache.LruCache;
import com.abbott.mutiimgloader.entity.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public class ImageLoader {

    private static final int MESSAGE_SEND_RESULT = 100;
    private static final int IMG_URL = 1000;

    private static final int CPU_COUNT = 4;
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1; //corePoolSize为CPU数加1
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1; //maxPoolSize为2倍的CPU数加1
    private static final long KEEP_ALIVE = 5L; //存活时间为5s

    public static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());


    //获取当前进程的可用内存（单位KB）
    int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
    int memoryCacheSize = maxMemory / 8;
    LruCache mMemoryCache = new LruCache<String, Bitmap>(memoryCacheSize) {

        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    };


    //主线程加载图片
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Result result = (Result) msg.obj;
            ImageView imageView = result.imageView;
            String url = (String) imageView.getTag(IMG_URL);
            if (url.equals(result.url)) {
                imageView.setImageBitmap(result.bitmap);
            } else {
                Log.w(TAG, "The url associated with imageView has changed");
            }
        }

        ;
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
        Bitmap bitmap = LoadFromMemory(url);
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
        return null;
    }

    private Bitmap LoadFromMemory(String url) {
        return null;
    }
}
