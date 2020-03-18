package com.hellozjf.wxgzh;

import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.Digester;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客服服务
 */
@Service
@Slf4j
public class CsadService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 添加客服
     * @param kfAccount
     * @param nickname
     * @param password
     * @return
     */
    public String addCsad(String kfAccount, String nickname, String password) {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map data = new HashMap();
        data.put("kf_account", kfAccount);
        data.put("nickname", nickname);
        data.put("password", DigestUtil.md5Hex(password, "UTF-8"));

        HttpEntity<Map> request = new HttpEntity<>(data, headers);

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(String.format(url, tokenService.getAccessToken()), request, byte[].class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            try {
                String result = new String(responseEntity.getBody(), "UTF-8");
                return result;
            } catch (UnsupportedEncodingException e) {
                log.error("e = ", e);
            }
        }
        return null;
    }
}
