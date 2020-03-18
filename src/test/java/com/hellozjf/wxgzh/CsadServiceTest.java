package com.hellozjf.wxgzh;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CsadServiceTest extends BaseTest {

    @Autowired
    private CsadService csadService;

    @Test
    public void addCsad() {
        String result = csadService.addCsad("account1", "客服1", "123456");
        log.debug("result = {}", result);
    }

}
