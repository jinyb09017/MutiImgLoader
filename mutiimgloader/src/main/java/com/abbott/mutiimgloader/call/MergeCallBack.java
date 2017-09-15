package com.abbott.mutiimgloader.call;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.List;

/**
 * @author jyb jyb_96@sina.com on 2017/9/14.
 * @version V1.0
 * @Description: 合并回调接口
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public interface MergeCallBack {
    Bitmap merge(List<Bitmap> bitmapArray, Context context, ImageView imageView);
}
