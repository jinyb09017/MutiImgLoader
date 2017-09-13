package com.abbott.mutiimgloader.cache;

import java.util.LinkedHashMap;

/**
 * @author jyb jyb_96@sina.com on 2017/9/13.
 * @version V1.0
 * @Description: add comment
 * @date 16-4-21 11:21
 * @copyright www.tops001.com
 */

public class LruCache<K, V> {

    private final LinkedHashMap<K, V> map;
    private final int maxSize;

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<K, V>(0, 0.75f, true);
    }

}
