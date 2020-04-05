package com.hellozjf.wxgzh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellozjf.wxgzh.vo.TempImageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 多媒体服务
 * @author Jingfeng Zhou
 */
@Slf4j
@Service
public class MediaService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 上传图片到微信临时素材
     * @param token
     * @param filePath
     * @param mediaType
     * @return
     */
    public TempImageVO uploadTempMediaFromFile(String token, String filePath, String mediaType) {

        String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);

        //设置请求体，注意是LinkedMultiValueMap
        File file = new File(filePath);
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", fileSystemResource);
        form.add("filename", file.getName());

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(String.format(url, token, mediaType), files, byte[].class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            try {
                String result = new String(responseEntity.getBody(), "UTF-8");
                log.debug("{}", result);
                TempImageVO tempImageVO = objectMapper.readValue(result, TempImageVO.class);
                return tempImageVO;
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }

    /**
     * 下载微信临时素材
     * @param mediaId
     * @return
     */
    public byte[] downloadTempMediaToBytes(String token, String mediaId) {
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
        ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(String.format(url, token, mediaId), byte[].class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            try {
                List<String> contentTypeList = responseEntity.getHeaders().get(HttpHeaders.CONTENT_TYPE);
                if (contentTypeList.contains("application/json") || contentTypeList.contains("text/plain")) {
                    String s = new String(responseEntity.getBody(), "UTF-8");
                    log.error("download image return {}", s);
                } else {
                    return responseEntity.getBody();
                }
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return null;
    }

    /**
     * 下载微信临时素材到某个文件
     * @param token
     * @param filePath
     * @param mediaId
     * @return
     */
    public boolean downloadTempMediaToFile(String token, String filePath, String mediaId) {
        byte[] data = downloadTempMediaToBytes(token, mediaId);
        if (data != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(data);
                return true;
            } catch (Exception e) {
                log.error("e = ", e);
            }
        }
        return false;
    }

    public void updatePermMediaFromFile(String filePath) {

    }
}
