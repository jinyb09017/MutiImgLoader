package com.abbott.mutiimgloader.weixin;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.widget.ImageView;

import com.abbott.mutiimgloader.call.MergeCallBack;

import java.util.List;

/**
 * @author jyb jyb_96@sina.com on 2017/9/14.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class WeixinMerge implements MergeCallBack {
    private Context context;

    @Override
    public Bitmap merge(List<Bitmap> bitmapArray, Context context, ImageView imageView) {
        this.context = context;

        return CombineBitmapTools.combimeBitmap(context, dip2px(context,75), dip2px(context,75),
                bitmapArray);
    }



    private int dip2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics()) + 0.5f);
    }



}
