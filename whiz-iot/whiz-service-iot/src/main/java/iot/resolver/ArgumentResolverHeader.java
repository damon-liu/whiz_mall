package iot.resolver;

import iot.entity.Klv;
import iot.entity.TopicHeader;
import iot.utils.HexResolverUtil;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 14:51
 */
public interface ArgumentResolverHeader<T extends TopicHeader> {

    Integer[] matchHeadType();

    /**
     * 判定是否走此路由
     *
     * @param hexArray
     * @return
     */
    default Integer type(List<String> hexArray) {
        HexResolverUtil resolver = new HexResolverUtil(hexArray);
        int type = resolver.resolver2Int(4, 6);
        return type;
    }

    /**
     * 上传匹配
     * @param hexArray
     * @return
     */
    default boolean uploadShould(List<String> hexArray){
        Integer type = type(hexArray);
        for (Integer matchType : matchHeadType()) {
            if(matchType.equals(type)){
                return true;
            }
        }
        return false;
    }

    /**
     * 将hex 转为可用java对象
     *
     * @param topic
     * @param hexArray
     * @return
     */
    T resolveArgument(String topic, List<String> hexArray);

    /**
     * 反向生成 字节
     *
     * @param header
     * @return
     */
    byte[] unResolveArgument(T header,List<Klv> param);
}
