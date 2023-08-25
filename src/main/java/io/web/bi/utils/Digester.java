package io.web.bi.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import static io.web.bi.constant.CommonConstant.SALT;

/**
 * Digester.
 *
 * @date 2023-06-05
 */
public class Digester {
    public static String DigestPassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException("加密值不能为空");
        }
        return DigestUtils.md5DigestAsHex((SALT + password).getBytes());
    }
}
