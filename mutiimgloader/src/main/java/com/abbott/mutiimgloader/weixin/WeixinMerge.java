package com.abbott.mutiimgloader.weixin;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.ViewGroup;
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

        // 画布的宽
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        int tempWidth;
        int tempHeight;
        if (lp != null) {
            tempWidth = dip2px(context, lp.width);
            tempHeight = dip2px(context, lp.height);
        } else {
            //否则给一个默认的高度
            tempWidth = dip2px(context, 70);
            tempHeight = dip2px(context, 70);
        }


        return CombineBitmapTools.combimeBitmap(context, tempWidth, tempHeight,
                bitmapArray);
    }

    @Override
    public String getMark() {
        return "wx@";
    }


    private int dip2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics()) + 0.5f);
    }



}
