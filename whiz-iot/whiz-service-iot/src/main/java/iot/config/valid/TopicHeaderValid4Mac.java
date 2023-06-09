package iot.config.valid;

import cn.hutool.core.util.HexUtil;
import com.google.common.collect.Lists;
import iot.entity.TopicHeader;
import iot.exception.HeadValidException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangpeng
 * @title: TopicHeaderValid4Head
 * @date 2020-05-289:37
 */
@Service
public class TopicHeaderValid4Mac implements TopicHeaderValidFunc {
    @Override
    public boolean validate(TopicHeader header, String hexString) {
        String toCheckHexStr = hexString.substring(20);
        // 16 进制字符串 转 二进制
        byte[] hexBytes  =  HexUtil.decodeHex(toCheckHexStr);
        byte[] md5Bytes = DigestUtils.md5(hexBytes);
        String md5Str = HexUtil.encodeHexStr(md5Bytes).toUpperCase();
        // 去字符串后8位
        md5Str = md5Str.substring(md5Str.length()-8);
        String mac = header.getMac().stream().collect(Collectors.joining());
        if(!mac.equals(md5Str)){
             throw new HeadValidException("mac校验失败，数据无效");
        }
        return true;
    }
    public List<Byte> getMac(List<Byte> all){
        List<Byte> result = Lists.newArrayList();
        List<Byte> toCheckByte = all.subList(10,all.size());
        byte[] hexBytes = new byte[toCheckByte.size()];
        for (int i = 0; i < toCheckByte.size(); i++) {
            hexBytes[i] = toCheckByte.get(i);
        }
        byte[] md5Bytes = DigestUtils.md5(hexBytes);
        for (int i =  md5Bytes.length-4; i < md5Bytes.length; i++) {
            result.add(md5Bytes[i]);
        }
        return result;
    }
}
