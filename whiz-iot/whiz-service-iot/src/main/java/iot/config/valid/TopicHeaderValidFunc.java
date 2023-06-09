package iot.config.valid;

import iot.entity.TopicHeader;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 14:38
 */
public interface TopicHeaderValidFunc {

    /**
     * 验证header
     * @param header
     * @param hexString 16进制数据值
     * @return
     */
    boolean validate(TopicHeader header, String hexString);

}
