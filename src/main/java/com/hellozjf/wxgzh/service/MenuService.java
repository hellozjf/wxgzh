package com.hellozjf.wxgzh.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 微信公众号菜单
 * 个人用户不能用
 * @author Jingfeng Zhou
 */
@Slf4j
@Service
public class MenuService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 创建菜单
     * @param accessToken
     * @param postData
     * @return
     */
    public String create(String accessToken, String postData) {
        String postUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
        ResponseEntity<byte[]> result = restTemplate.postForEntity(String.format(postUrl, accessToken), postData, byte[].class);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return new String(result.getBody(), "UTF-8");
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }

    /**
     * 查询菜单
     * @param accessToken
     * @return
     */
    public String query(String accessToken) {
        String postUrl = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";
        ResponseEntity<byte[]> result = restTemplate.postForEntity(String.format(postUrl, accessToken), null, byte[].class);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return new String(result.getBody(), "UTF-8");
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }

    /**
     * 删除菜单
     * @param accessToken
     * @return
     */
    public String delete(String accessToken) {
        String postUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";
        ResponseEntity<byte[]> result = restTemplate.postForEntity(String.format(postUrl, accessToken), null, byte[].class);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return new String(result.getBody(), "UTF-8");
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }

    /**
     * 获取自定义菜单配置接口
     * @param accessToken
     * @return
     */
    public String getCurrentSelfmenuInfo(String accessToken) {
        String postUrl = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=%s";
        ResponseEntity<byte[]> result = restTemplate.postForEntity(String.format(postUrl, accessToken), null, byte[].class);
        if (result.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return new String(result.getBody(), "UTF-8");
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }
}
