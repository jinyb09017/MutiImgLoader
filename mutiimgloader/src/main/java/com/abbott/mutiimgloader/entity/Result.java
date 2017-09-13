package com.abbott.mutiimgloader.entity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * @author jyb jyb_96@sina.com on 2017/9/8.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class Result {
    public Bitmap bitmap;
    public String url;
    public ImageView imageView;
    public View joinView;

    public ArrayList<Bitmap> bitmaps;

    public Result(Bitmap bitmap, String url, ImageView imageView) {
        this.bitmap = bitmap;
        this.url = url;
        this.imageView = imageView;
    }

    public Result(ArrayList<Bitmap> bitmaps, String url, View joinView) {
        this.bitmaps = bitmaps;
        this.url = url;
        this.joinView = joinView;
    }
}
