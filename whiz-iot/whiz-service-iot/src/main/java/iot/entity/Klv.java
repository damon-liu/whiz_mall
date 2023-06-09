package iot.entity;

import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import iot.utils.HexUtils;
import iot.enums.IotTcpDataTypeEnum;
import iot.enums.TslDataTypeEnum;
import iot.enums.TslDbTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhangpeng
 * @title: Klv
 * @date 2020-06-0812:20
 */
@Data
@NoArgsConstructor
public class Klv {
    private int key;
    private int len;
    private Object val;
    private TslDataTypeEnum type;
    // 暂时用不上
    private IotTcpDataTypeEnum tcpDataTypeEnum;
    /**
     * 数据库存储类型 主要 是枚举类型的时候做判断使用
     */
    private TslDbTypeEnum dbTypeEnum;
    /**
     * 如果是集合类型 ，存在此子集
     */
    private List<List<Klv>> child;

    public Klv(String tcpKey, String type, Object val, int len) {
        // 16进制字符串转 int 由于tcp定义的key是16进制
        this.key = Integer.parseInt(tcpKey, 16);
        this.type = TslDataTypeEnum.getEnum(type);
        this.val = val;
        this.len = len;
    }

    public Klv(String tcpKey, TslDataTypeEnum type, Object val, Integer len, IotTcpDataTypeEnum tcpDataTypeEnum, TslDbTypeEnum dbTypeEnum) {
        // 16进制字符串转 int 由于tcp定义的key是16进制
        this.key = Integer.parseInt(tcpKey, 16);
        this.type = type;
        this.val = val;
        this.len = len;
        this.tcpDataTypeEnum = tcpDataTypeEnum;
        this.dbTypeEnum = dbTypeEnum;
    }

    public List<Byte> getKlvBytes() {
        int lLen = 1;
        List<Byte> byteList = Lists.newArrayList();
        if (this.val == null || Objects.equals("", this.val.toString())) {
            return byteList;
        }
        List<Byte> vals = Lists.newArrayList();
        switch (this.type) {
            // 枚举类型特殊处理
            case ENUM:
                //vals = getHex4Enum(this.val,this.tcpDataTypeEnum,this.dbTypeEnum,len);
                if (IotTcpDataTypeEnum.STRING.getVal().equals(this.tcpDataTypeEnum.getVal())) {
                    vals = HexUtils.toHexByte(this.val.toString());
                    this.len = vals.size();
                } else {
                    vals = HexUtils.toHexByte(Long.parseLong(String.valueOf(this.val)), this.len);
                }
                break;
            case DOUBLE:
                vals = HexUtils.toHexByte(Double.parseDouble(this.val.toString()), 8);

                break;
            case DATE:
                Date date = (Date) this.val;
                Long time = date.getTime() / 1000;
                vals = HexUtils.toHexByte(time, 4);
                this.len = 4;
                break;
            case STRING:
                if (IotTcpDataTypeEnum.STRING.getVal().equals(this.tcpDataTypeEnum.getVal())) {
                    vals = HexUtils.toHexByte(this.val.toString());
                    this.len = vals.size();
                } else {
                    vals = HexUtils.toHexByte(Long.parseLong(String.valueOf(this.val)), this.len);
                }
                break;
            case LIST:
                if (this.child != null) {
                    // 数组固定码
                    byteList.addAll(HexUtils.toHexByte(61698, 2));
                    // 长度
                    vals = this.getListKlv(this.child);
                    this.len = vals.size() + 2;
                    byteList.addAll(HexUtils.toHexByte(this.len, 2));
                    // 数组类型，无长度
                    lLen = 0;

                }
                break;
//            case BOOLEAN:
//                Boolean booleanV = (Boolean) this.val;
//                vals = booleanV ? HexUtils.toHexByte(1, this.len) : HexUtils.toHexByte(0, this.len);
//                break;
            case INTEGER:
            case LONG:
            default:
                long longValue = NumberUtil.toBigDecimal(String.valueOf(this.val)).longValue();
                vals = HexUtils.toHexByte(longValue, this.len);
                break;
        }
        byteList.addAll(HexUtils.toHexByte(this.key, 2));
        // 数组类型，无长度 排除数组类型
        if (lLen > 0) {
            byteList.addAll(HexUtils.toHexByte(this.len, lLen));
        }

        byteList.addAll(vals);
        return byteList;
    }

    /**
     * 当定义为枚举类型的时候 转换为16进制
     *
     * @param val
     * @param tcpDataTypeEnum
     * @param dbTypeEnum
     * @return
     */
    private List<Byte> getHex4Enum(Object val, IotTcpDataTypeEnum tcpDataTypeEnum, TslDbTypeEnum dbTypeEnum, int len) {
        List<Byte> hexs = Lists.newArrayList();
        if (IotTcpDataTypeEnum.STRING.getVal().equals(tcpDataTypeEnum.getVal())) {
            hexs = HexUtils.toHexByte(val.toString());
            len = hexs.size();
        } else {
            hexs = HexUtils.toHexByte(Long.parseLong(String.valueOf(val)), len);
        }
        return hexs;
    }

    private void getHex4TcpType(Object val, IotTcpDataTypeEnum tcpDataTypeEnum, List<Byte> vals, int len) {
        if (IotTcpDataTypeEnum.STRING.getVal().equals(tcpDataTypeEnum.getVal())) {
            vals = HexUtils.toHexByte(val.toString());
            len = vals.size();
        } else {
            vals = HexUtils.toHexByte(Long.parseLong(String.valueOf(val)), len);
        }
        return;
    }

    /**
     * 封装为数组形式的byte
     *
     * @param childList
     * @return
     */
    private List<Byte> getListKlv(List<List<Klv>> childList) {
        List<Byte> byteList = Lists.newArrayList();
        for (int i = 0; i < childList.size(); i++) {
            List<Klv> klvList = childList.get(i);
            for (Klv klv : klvList) {
                byteList.addAll(klv.getKlvBytes());
            }
            if (i != childList.size() - 1) {
                final byte dh = 0X2C;
                byteList.add(dh);
            }
        }
        return byteList;
    }
}
