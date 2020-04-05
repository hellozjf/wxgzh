package com.hellozjf.wxgzh.schedule;

import com.hellozjf.wxgzh.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 获取token的调度程序
 * @author Jingfeng Zhou
 */
@Component
public class TokenSchedule {

    @Autowired
    private TokenService tokenService;

    /**
     * 每分钟执行一次获取token
     */
    @Scheduled(cron = "0 * * * * ?")
    public void getTokenTask() {
        tokenService.obtainToken();
    }
}
