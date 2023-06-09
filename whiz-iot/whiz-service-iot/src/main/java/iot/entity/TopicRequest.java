package iot.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zhangpeng
 * @title: TopicRequest
 * @date 2020-05-2810:07
 */
@Data
@NoArgsConstructor
public class TopicRequest {
    private TopicHeader topicHeader;
    private String hexStr;
    /**
     * 数据接收时间
     */
    private Long serverRecvTime;
    // 原始数据
    private byte[] payload;
    // 参数
    //private Map<String,Object> param2;
    // 数据hex int 对应的值
    private LinkedHashMap<String,List<String>> hexVal;
    // 16进制 转为数组
    private List<String> hexArray;

    public TopicRequest(TopicHeader topicHeader, String hexStr, List<String> hexArray){
        this.topicHeader = topicHeader;
        this.hexArray = hexArray;
        this.hexStr = hexStr;
    }
}
