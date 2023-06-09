package iot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhangpeng
 * @title: TopicHeader
 * @date 2020-05-2712:32
 */
@Data
@NoArgsConstructor
public abstract class TopicHeader {
    /**
     * 数据总长度
     */
    private int dataLength;
    private int head;
    private int version;

    /**
     * 数据类型
     */
    private int type;
    /**
     * 校验码
     */
    private List<String> mac;
    /**
     * 时间戳
     */
    private Long time;
    /**
     * 时间戳
     */
    private int timezone;

    private int topicCode;
    private int len1;
    private int len2;
    private int len3;
    private int rsvd;
    /**
     * 原productSn
     */
    private String clientCode;
    /**
     * 后续存入
     */
    private String modemSn;

    private Long identifierSn;

    public TopicHeader(int topicCode, Long identifierSn) {
        this.dataLength = 100;
        this.identifierSn = identifierSn;
        this.head = 1;
        this.version = 1;
        this.type = 61;
        this.time = System.currentTimeMillis() / 1000;
        this.timezone = 8;
        this.topicCode = topicCode;
        this.len1 = 0;
        this.len2 = 0;
        this.len3 = 0;
        this.rsvd = 0;
    }

    public TopicHeader(int topicCode, int type, Long identifierSn, int version) {
        this.dataLength = 100;
        this.head = 1;
        this.version = version;
        this.identifierSn = identifierSn;
        this.type = type;
        this.time = System.currentTimeMillis() / 1000;
        this.timezone = 8;
        this.topicCode = topicCode;
        this.len1 = 0;
        this.len2 = 0;
        this.len3 = 0;
        this.rsvd = 0;
    }

    /**
     * 获取头部 hex
     *
     * @return
     */
    public abstract List<Byte> getHexHeadByte();

    /**
     * 组合成设备需要的hex
     *
     * @param hexHeadByte
     * @param bodyBytes
     * @return
     */
    public abstract byte[] getHexByte(List<Byte> hexHeadByte, List<Byte> bodyBytes);
}
