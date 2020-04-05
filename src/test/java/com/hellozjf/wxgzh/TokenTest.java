package com.hellozjf.wxgzh;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Jingfeng Zhou
 */
@Slf4j
public class TokenTest extends BaseTest {

    @Test
    public void genToken() {
        String token = RandomUtil.randomString(32);
        log.debug("token = {}", token);
    }
}
