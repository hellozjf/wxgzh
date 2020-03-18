package com.hellozjf.wxgzh;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@Data
public class TokenService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;
    private int expiresIn = 0;

    @PostConstruct
    public void postConstruct() {
        getToken();
    }

    @Scheduled(cron = "0 * * * * ?")
    public void getTokenTask() {
        getToken();
    }

    private void getToken() {
        expiresIn -= 60;
        if (expiresIn <= 0) {
            String appId = "wx9749540aac40f459";
            String appSecret = "237ab42626b00ba79594bb58e60b41d1";
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
            ResponseEntity<byte[]> result = restTemplate.getForEntity(String.format(url, appId, appSecret), byte[].class);
            if (result.getStatusCode().equals(HttpStatus.OK)) {
                try {
                    JsonNode jsonNode = objectMapper.readTree(new String(result.getBody(), "UTF-8"));
                    accessToken = jsonNode.get("access_token").asText();
                    expiresIn = jsonNode.get("expires_in").asInt();
                    log.debug("accessToken = {}", accessToken);
                } catch (Exception e) {
                    log.error("e = ", e);
                }
            }
        }
    }
}
