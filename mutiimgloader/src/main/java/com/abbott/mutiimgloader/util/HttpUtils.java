package com.abbott.mutiimgloader.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author jyb jyb_96@sina.com on 2017/9/8.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class HttpUtils {


    public static void close(BufferedInputStream bis) {
        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(InputStream is) {
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void close(BufferedOutputStream bos) {
        try {
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
