package iot.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zhangpeng
 * @title: HexUtils
 * @date 2020-06-0810:33
 */
public class HexUtils {
    public static String toHex(int value, int d) {
        String a = Integer.toHexString(value);
        return String.format("%02x", value);
    }

    public static String toHex(Long value, int d) {
        return String.format("%0" + d + "d", Long.toHexString(value));
    }

    public static List<Byte> toHexByte(int value, int len) {
        List<Byte> byteList = Lists.newArrayList();
        for (int i = len - 1; i >= 0; i--) {
            byte vv = (byte) ((value >> 8 * i) & 0xFF);
            byteList.add(vv);
        }
        return byteList;
    }

    public static byte[] int2Bytes(int num) {
        byte[] bytes = new byte[4];
        //通过移位运算，截取低8位的方式，将int保存到byte数组
        bytes[0] = (byte) (num >>> 24);
        bytes[1] = (byte) (num >>> 16);
        bytes[2] = (byte) (num >>> 8);
        bytes[3] = (byte) num;
        return bytes;
    }

    public static List<Byte> toHexByte(long value, int len) {
        List<Byte> byteList = Lists.newArrayList();
        for (int i = len - 1; i >= 0; i--) {
            byte vv = (byte) ((value >> 8 * i) & 0xFF);
            byteList.add(vv);
        }
        return byteList;
    }

    public static List<Byte> toHexByte(double value, int len) {
        return toHexByte(Double.doubleToLongBits(value), 8);
    }

    public static List<Byte> toHexByte(String str, int len) {
        List<Byte> byteList = Lists.newArrayList();
        byte[] array = str.getBytes();
        for (int i = 0; i < len; i++) {
            if (i < array.length) {
                byteList.add(array[i]);
            } else {
                byteList.add(Byte.valueOf("0"));
            }

        }
        return byteList;
    }
    public static List<Byte> toHexByte(String str) {
        List<Byte> byteList = Lists.newArrayList();
        byte[] array = str.getBytes();
        for (int i = 0; i < array.length; i++) {
            byteList.add(array[i]);
        }
        return byteList;
    }

    /**
     * 转为有符号
     *
     * @param hex
     * @param len
     * @return
     */
    public static long getUnitLong(String hex, int len) {
        Long hexLong = Long.parseLong(hex, 16);
        switch (len) {
            case 1:
                return hexLong.byteValue();
            case 2:
                return hexLong.shortValue();
            case 4:
                return hexLong.intValue();
            case 8:
            default:
                return hexLong;
        }
    }

}
