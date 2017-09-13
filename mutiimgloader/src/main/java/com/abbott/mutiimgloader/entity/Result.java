package com.abbott.mutiimgloader.entity;

import android.graphics.Bitmap;
import android.widget.ImageView;

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

    public Result(Bitmap bitmap, String url, ImageView imageView) {
        this.bitmap = bitmap;
        this.url = url;
        this.imageView = imageView;
    }
}
