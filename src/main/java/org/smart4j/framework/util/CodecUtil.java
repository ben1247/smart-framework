package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 编码与解码操作工具类
 * Created by yuezhang on 17/10/6.
 */
public final class CodecUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将URL编码
     * @param source
     * @return
     */
    public static String encodeURL(String source){
        String target;
        try {
            target = URLEncoder.encode(source,"UTF-8");
        } catch (Exception e) {
            LOGGER.error("encode url failure" , e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将URL解码
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source,"UTF-8");
        } catch (Exception e) {
            LOGGER.error("decode url failure" , e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * MD5 加密
     * @param source
     * @return
     */
    public static String md5(String source){
        return DigestUtils.md2Hex(source);
    }

}
