package iot.utils;

import cn.hutool.core.util.HexUtil;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import iot.config.valid.TopicHeaderValid4Mac;
import iot.entity.Klv;
import iot.entity.TopicHeaderA;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zhangpeng
 * @title: HexResolverUtil
 * @date 2020-05-2715:49
 */
public class HexResolverUtil {
    private final List<String> hexList;
    public static final int MAC_INDEX = 6;
    public static final int MAC_LEN = 4;

    public HexResolverUtil(List<String> hexArray) {
        this.hexList = hexArray;
    }

    /**
     * 左封右闭
     * 转换为int类型
     * 【 )
     *
     * @param start
     * @param end
     * @return
     */
    public int resolver2Int(int start, int end) {
        String hexStr = this.getHexStr(start, end);
        return Integer.parseInt(hexStr, 16);
    }

    public long resolver2Long(int start, int end) {
        String hexStr = this.getHexStr(start, end);
        return Long.parseLong(hexStr, 16);
    }

    public long resolver2ULong(int start, int end, int len) {
        String hexStr = this.getHexStr(start, end);
        return HexUtils.getUnitLong(hexStr, len);
    }

    public double resolver2Double(int start, int end) {
        String hexStr = this.getHexStr(start, end);
        return Double.longBitsToDouble(Long.parseLong(hexStr, 16));
    }

    public String resolver2Str(int start, int end) {
        String hexStr = this.getHexStrIgnore00(start, end);
        return HexUtil.decodeHexStr(hexStr);
    }

    public String getHexStr(int start, int end) {
        if (start >= end) {
            return this.hexList.get(start);
        }
        StringBuffer hex = new StringBuffer();
        for (int i = start; i < end; i++) {
            hex.append(this.hexList.get(i));
        }
        return hex.toString();
    }

    /**
     * 过滤掉00
     *
     * @param start
     * @param end
     * @return
     */
    private String getHexStrIgnore00(int start, int end) {
        if (this.hexList.isEmpty()) {
            return "";
        }
        if (start >= end) {
            return this.hexList.get(start);
        }
        StringBuffer hex = new StringBuffer();
        for (int i = start; i < end; i++) {
            String h = this.hexList.get(i);
            if (Objects.equal(h, "00")) {
                continue;
            }
            hex.append(h);
        }
        return hex.toString();
    }

    public static List<Byte> chang2byte(List<Klv> param) {
        List<Byte> byteList = Lists.newArrayList();
        param.forEach(klv -> {
            if (klv != null) {
                byteList.addAll(klv.getKlvBytes());
            }
        });
        return byteList;
    }

    /**
     * 获取下发命令
     *
     * @param productSn
     * @param modemSn
     * @param identify
     * @param param
     * @return
     */
    public static byte[] pushHex(String tcptopicCode, String productSn, String modemSn, Integer identify, List<Klv> param) {
        // header
        TopicHeaderA header = new TopicHeaderA(productSn, modemSn, Integer.parseInt(tcptopicCode), identify.intValue());
        List<Byte> bytes = header.getHexHeadByte();
        // 参数值 和参数转换
        List<Byte> dataBytes = chang2byte(param);
        bytes.addAll(dataBytes);
        // 数据总长度
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        // 总长度
        List<Byte> len = HexUtils.toHexByte(bytes.size() - 2, 2);
        result[0] = len.get(0);
        result[1] = len.get(1);

        // 计算 len1 len2 len3
        int len0 = bytes.size() - 2 - 22;
        int l1 = (len0 >> 16) & 0xFF;
        int l2 = (len0 >> 8) & 0xFF;
        int l3 = (len0 >> 0) & 0xFF;
        result[21] = (byte) l1;
        result[22] = (byte) l2;
        result[23] = (byte) l3;
        // mac 问题
        List<Byte> resultList = Lists.newArrayList();
        for (int i = 0; i < result.length; i++) {
            resultList.add(result[i]);
        }
        List<Byte> mac = new TopicHeaderValid4Mac().getMac(resultList);
        for (int i = 0; i < MAC_LEN; i++) {
            result[MAC_INDEX + i] = mac.get(i);
        }
        return result;
    }


    private static final String KEY_2C = "2C";

    public List<LinkedHashMap<String, List<String>>> resolver2List() {
        List<LinkedHashMap<String, List<String>>> result = Lists.newArrayList();

        LinkedHashMap<String, List<String>> hexMap = new LinkedHashMap<>();
        // 解析hex 数据包
        List<String> dataHex = this.hexList;
        int index = 0;
        final boolean isLast = false;
        while (index < dataHex.size() - 3) {
            int end = index;
            // ，标识符
            if (index > 0) {
                String comma = this.getHexStr(index, end + 1);
                if (KEY_2C.equals(comma.toUpperCase())) {
                    result.add(hexMap);
                    hexMap = new LinkedHashMap<>();
                    index++;
                    continue;
                }
            }
            // 属性编号
            String dataCode = this.getHexStr(index, end + 2);
            end += 2;
            // 数据长度
            int length = this.resolver2Int(end, end + 1);
            end += 1;
            // 数据
            List<String> val = dataHex.subList(end, end + length);
            index = end + length;
            hexMap.put(dataCode, val);
            if (index >= dataHex.size() - 3) {
                result.add(hexMap);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(Long.parseLong("31363130353230303635303030", 16));
    }
}

