package iot.resolver;

import com.google.common.collect.Lists;
import iot.utils.HexUtils;
import iot.config.valid.TopicHeaderValid4Mac;
import iot.entity.Klv;
import iot.entity.TopicHeaderA;
import iot.utils.HexResolverUtil;

import java.util.List;

/**
 * Description:
 *
 * @author damon.liu
 * Date 2023-06-08 14:52
 */
public class ArgumentResolverHeader4A implements ArgumentResolverHeader<TopicHeaderA>{

    @Override
    public Integer[] matchHeadType() {
        return new Integer[]{61};
    }

    @Override
    public TopicHeaderA resolveArgument(String topic, List<String> hexArray) {
        HexResolverUtil resolver = new HexResolverUtil(hexArray);
        TopicHeaderA header = new TopicHeaderA();
        header.setDataLength(resolver.resolver2Int(0, 2));
        header.setHead(resolver.resolver2Int(2, 3));
        header.setVersion(resolver.resolver2Int(3, 4));
        header.setType(resolver.resolver2Int(4, 6));
        header.setMac(hexArray.subList(6, 10));
        header.setTime(resolver.resolver2Long(10, 14) * 1000);
        header.setTimezone(resolver.resolver2Int(14, 16));
        header.setIdentifier(resolver.resolver2Int(16, 20));

        header.setTopicCode(resolver.resolver2Int(20, 21));
        header.setLen1(resolver.resolver2Int(21, 22));
        header.setLen2(resolver.resolver2Int(22, 23));
        header.setLen3(resolver.resolver2Int(23, 24));
        // 成功的。但是解析错误
        header.setProductSn(resolver.resolver2Str(24, 30));
        header.setModemSn(resolver.resolver2Str(30, 46));
        header.setIdentifierSn((long) header.getIdentifier());
        return header;
    }

    @Override
    public byte[] unResolveArgument(TopicHeaderA header, List<Klv> klvList) {
        // header
        List<Byte> bytes = header.getHexHeadByte();
        // 参数值 和参数转换
        List<Byte> dataBytes = HexResolverUtil.chang2byte(klvList);
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
        for (int i = 0; i < HexResolverUtil.MAC_LEN; i++) {
            result[HexResolverUtil.MAC_INDEX + i] = mac.get(i);
        }

        return result;
    }
}
