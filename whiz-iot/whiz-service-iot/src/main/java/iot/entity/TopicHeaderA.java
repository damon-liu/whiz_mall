package iot.entity;

import cn.hutool.core.util.HexUtil;
import com.google.common.collect.Lists;
import iot.utils.HexUtils;
import iot.config.valid.TopicHeaderValid4Mac;
import iot.utils.HexResolverUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangpeng
 * @title: TopicHeader
 * @date 2020-05-2712:32
 */
@Data
@NoArgsConstructor
public class TopicHeaderA extends TopicHeader {
    private String productSn;
    private String modemSn;
    private int identifier;

    public String getHexHead(String mac) {
        StringBuffer sb = new StringBuffer();
        sb.append(HexUtils.toHex(getDataLength(), 4));
        sb.append(HexUtils.toHex(getHead(), 2));
        sb.append(HexUtils.toHex(getVersion(), 2));
        sb.append(HexUtils.toHex(getType(), 4));

        sb.append(HexUtil.encodeHexStr(mac));
        sb.append(HexUtils.toHex(getTime(), 8));
        sb.append(HexUtils.toHex(getTimezone(), 4));
        sb.append(HexUtils.toHex(getTopicCode(), 2));
        sb.append(HexUtils.toHex(getLen1(), 2));
        sb.append(HexUtils.toHex(getLen2(), 2));
        sb.append(HexUtils.toHex(getLen3(), 2));

        sb.append(HexUtil.encodeHexStr(productSn));
        sb.append(HexUtils.toHex(getRsvd(), 2));
        sb.append(HexUtil.encodeHexStr(modemSn));

        return sb.toString();
    }
    @Override
    public List<Byte> getHexHeadByte() {
        List<Byte> byteList = Lists.newArrayList();
        byteList.addAll(HexUtils.toHexByte(getDataLength(), 2));

        byteList.addAll(HexUtils.toHexByte(getHead(), 1));
        byteList.addAll(HexUtils.toHexByte(getVersion(), 1));
        byteList.addAll(HexUtils.toHexByte(getType(), 2));
        // mac 占位置
        byteList.add(Byte.valueOf("0"));
        byteList.add(Byte.valueOf("0"));
        byteList.add(Byte.valueOf("0"));
        byteList.add(Byte.valueOf("0"));

        byteList.addAll(HexUtils.toHexByte(getTime(), 4));
        byteList.addAll(HexUtils.toHexByte(getTimezone(), 2));
        // identifier
        byteList.addAll(HexUtils.toHexByte(getIdentifier(), 4));
        byteList.addAll(HexUtils.toHexByte(getTopicCode(), 1));
        byteList.addAll(HexUtils.toHexByte(getLen1(), 1));
        byteList.addAll(HexUtils.toHexByte(getLen2(), 1));
        byteList.addAll(HexUtils.toHexByte(getLen3(), 1));

        byteList.addAll(HexUtils.toHexByte(productSn, 6));
        byteList.addAll(HexUtils.toHexByte(getRsvd(), 2));
        byteList.addAll(HexUtils.toHexByte(modemSn, 16));

        return byteList;
    }
    @Override
    public byte[] getHexByte(List<Byte> hexHeadByte, List<Byte> bodyBytes) {
        // 参数值 和参数转换
        List<Byte> bytes = new ArrayList<>();
        bytes.addAll(hexHeadByte);
        bytes.addAll(bodyBytes);
        // 数据总长度
        byte[] result = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            result[i] = bytes.get(i);
        }
        // 总长度
        List<Byte> len = HexUtils.toHexByte(bytes.size()-2,2);
        result[0] = len.get(0);
        result[1] = len.get(1);

        // 计算 len1 len2 len3
        int len0 = bytes.size()-2 - 22;
        int l1 = (len0 >> 16) & 0xFF;
        int l2 = (len0 >> 8) & 0xFF;
        int l3 = (len0 >> 0) & 0xFF;
        result[21] = (byte)l1;
        result[22] = (byte)l2;
        result[23] = (byte)l3;
        // mac 问题
        List<Byte> resultList = Lists.newArrayList();
        for (int i = 0; i < result.length; i++) {
            resultList.add(result[i]);
        }
        List<Byte> mac = new TopicHeaderValid4Mac().getMac(resultList);
        for (int i = 0; i < HexResolverUtil.MAC_LEN; i++) {
            result[HexResolverUtil.MAC_INDEX +i] = mac.get(i);
        }
        return result;

    }
    public TopicHeaderA(String productSn, String modemSn, int tcpTopicCode, int identifier) {
        super(tcpTopicCode,(long)identifier);
        this.identifier = identifier;
        this.setIdentifierSn((long)identifier);
        this.productSn = productSn;
        this.modemSn = modemSn;
    }
}
