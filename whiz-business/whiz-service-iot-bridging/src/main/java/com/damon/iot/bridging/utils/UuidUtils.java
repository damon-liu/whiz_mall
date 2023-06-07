package com.damon.iot.bridging.utils;

/**
 * @description: 分布式主键生成器
 * @author: ICE-kolin.liu
 * @datetime: 2019-08-23 10:16
 */
public class UuidUtils {

    public static String getUuid(){
        return UuidHexGeneratorUtil.generate();
    }


    public static void main(String[] args) {
        System.out.println(UuidHexGeneratorUtil.generate());
    }
}
