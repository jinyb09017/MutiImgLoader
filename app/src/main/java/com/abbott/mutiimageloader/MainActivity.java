package com.abbott.mutiimageloader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.abbott.mutiimgloader.circularavatar.CircularImageView;
import com.abbott.mutiimgloader.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    ArrayList<Bitmap> mBmps1 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps2 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps3 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps4 = new ArrayList<Bitmap>();
    ArrayList<Bitmap> mBmps5 = new ArrayList<Bitmap>();

    CircularImageView mImageView1;
    CircularImageView mImageView2;
    CircularImageView mImageView3;
    CircularImageView mImageView4;
    CircularImageView mImageView5;

    static List<String> urls = new ArrayList<>();

    static {
        urls.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1505294718&di=b6934dd570c0c6962a8dbbb12eac27f5&src=http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201204/20120412123914329.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505304803685&di=3a859c35334d8d86177f0cfb8adfdc65&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123926750.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505304803685&di=3a859c35334d8d86177f0cfb8adfdc65&imgtype=0&src=http%3A%2F%2Fwww.zhlzw.com%2FUploadFiles%2FArticle_UploadFiles%2F201204%2F20120412123926750.jpg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test data
        Bitmap avatar1 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow1);
        Bitmap avatar2 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow2);
        Bitmap avatar3 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow3);
        Bitmap avatar4 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow4);
        Bitmap avatar5 = BitmapFactory.decodeResource(getResources(), R.drawable.headshow5);

        mBmps1.add(avatar1);
        mBmps2.add(avatar1);
        mBmps3.add(avatar1);
        mBmps4.add(avatar1);
        mBmps5.add(avatar1);

        mBmps2.add(avatar2);
        mBmps3.add(avatar2);
        mBmps4.add(avatar2);
        mBmps5.add(avatar2);

        mBmps3.add(avatar3);
        mBmps4.add(avatar3);
        mBmps5.add(avatar3);

        mBmps4.add(avatar4);
        mBmps5.add(avatar4);

        mBmps5.add(avatar5);

        mImageView1 = (CircularImageView) findViewById(R.id.circularImageView1);
        mImageView2 = (CircularImageView) findViewById(R.id.circularImageView2);
        mImageView3 = (CircularImageView) findViewById(R.id.circularImageView3);
        mImageView4 = (CircularImageView) findViewById(R.id.circularImageView4);
        mImageView5 = (CircularImageView) findViewById(R.id.circularImageView5);

        mImageView1.setImageBitmaps(mBmps1);
        mImageView2.setImageBitmaps(mBmps2);
        mImageView3.setImageBitmaps(mBmps3);
        mImageView4.setImageBitmaps(mBmps4);
        mImageView5.setImageBitmaps(mBmps5);

        new ImageLoader(this).displayImages(urls,mImageView1,200,200);

        CircularImageView mImageView21 = (CircularImageView) findViewById(R.id.circularImageView21);
        CircularImageView mImageView22 = (CircularImageView) findViewById(R.id.circularImageView22);
        CircularImageView mImageView23 = (CircularImageView) findViewById(R.id.circularImageView23);
        CircularImageView mImageView24 = (CircularImageView) findViewById(R.id.circularImageView24);
        CircularImageView mImageView25 = (CircularImageView) findViewById(R.id.circularImageView25);

        mImageView21.setImageBitmaps(mBmps1);
        mImageView22.setImageBitmaps(mBmps2);
        mImageView23.setImageBitmaps(mBmps3);
        mImageView24.setImageBitmaps(mBmps4);
        mImageView25.setImageBitmaps(mBmps5);

        CircularImageView mImageView31 = (CircularImageView) findViewById(R.id.circularImageView31);
        CircularImageView mImageView32 = (CircularImageView) findViewById(R.id.circularImageView32);
        CircularImageView mImageView33 = (CircularImageView) findViewById(R.id.circularImageView33);
        CircularImageView mImageView34 = (CircularImageView) findViewById(R.id.circularImageView34);
        CircularImageView mImageView35 = (CircularImageView) findViewById(R.id.circularImageView35);

        mImageView31.setImageBitmaps(mBmps1);
        mImageView32.setImageBitmaps(mBmps2);
        mImageView33.setImageBitmaps(mBmps3);
        mImageView34.setImageBitmaps(mBmps4);
        mImageView35.setImageBitmaps(mBmps5);

    }

    public void onBitmapBtnClick(View view) {
        startActivity(new Intent(this, BitmapActivity.class));
    }
}
