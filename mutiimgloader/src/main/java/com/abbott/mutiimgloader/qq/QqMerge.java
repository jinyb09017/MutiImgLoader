package com.abbott.mutiimgloader.qq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class QqMerge implements MergeCallBack {


    @Override
    public Bitmap merge(List<Bitmap> bitmapArray, Context context, ImageView imageView) {
        if (bitmapArray.size() < 1 && bitmapArray.size() > 9) {
            return null;
        }
        // 先取一个获取宽和高
        Bitmap tempBitmap =  bitmapArray.get(0);
        if (tempBitmap == null) {
            return null;
        }
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

        Bitmap canvasBitmap = Bitmap.createBitmap(tempWidth, tempHeight,
                Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(canvasBitmap);
        localCanvas.drawColor(Color.WHITE);
        JoinBitmaps.join(localCanvas, Math.min(tempWidth, tempHeight),
                bitmapArray);
        return canvasBitmap;
    }

    private  int dip2px(Context context, float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
