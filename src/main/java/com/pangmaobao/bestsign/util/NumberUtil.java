package com.pangmaobao.bestsign.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author: he.feng
 * @date: 18:03 2018/1/9
 * @desc:
 **/
public class NumberUtil {

    /**
     * 生成随机数
     * @param min min
     * @param max max
     * @return the string
     */
    public static String randNumber(Long min, Long max) {
        Long temp = Math.round(Math.random() * (max - min) + min);
        return temp.toString();
    }


}
