package com.abbott.mutiimgloader.entity;

/**
 * @author jyb jyb_96@sina.com on 2017/9/15.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class CombineBitmapEntity {
    public float x;
    public float y;
    public float width;
    public float height;
    public static int devide = 1;
    public int index = -1;

    @Override
    public String toString() {
        return "MyBitmap [x=" + x + ", y=" + y + ", width=" + width
                + ", height=" + height + ", devide=" + devide + ", index="
                + index + "]";
    }
}
