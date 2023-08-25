package io.web.bi.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;

import static io.web.bi.constant.CommonConstant.SALT;

/**
 * EncryKeyGenerator.
 * 秘钥生成
 *
 * @date 2023-05-30
 */
public class EncryKeyGenerator {

    public static String gen(String content, String salt) {
        return DigestUtil.md5Hex(content + salt + RandomUtil.randomNumbers(8));
    }

    public static String gen(String content) {
        return gen(content, SALT);
    }
}
