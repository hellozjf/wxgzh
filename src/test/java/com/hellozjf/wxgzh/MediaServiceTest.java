package com.hellozjf.wxgzh;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class MediaServiceTest extends BaseTest {

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @Test
    public void testUpload() throws Exception {
        String token = tokenService.getAccessToken();
        TempImageVO tempImageVO = mediaService.uploadTempMediaFromFile(token, "d:\\tmp\\1.jpg", "image");
        Assert.assertNotNull(tempImageVO);
        log.debug("token = {}", tokenService.getAccessToken());
        log.debug("result = {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(tempImageVO));
    }

    @Test
    public void testDownload() throws Exception {
        String token = "31_wWXKlPGVOShjsV22VLP6R_29AnFg96nLAkdJJBhO3UwSr7c1WQbyHi7SPLwLg7uuCmz575faFtg3V3dRfLzhhxO4pzCnz7Plk6ZuPFsspXqh_KU8QGA0cBpM3iN6NQFHlW7ZMyhS_KJq5vTURKCjABAXQI";
        boolean ret = mediaService.downloadTempMediaToFile(token, "d:\\tmp\\2.jpg", "z-DTSea5ee8PyzUcveq6U-bRz-aPj5PhdFqRlXKyxSpTCJQXjRj9JUPvxVQyi2p-");
        Assert.assertTrue(ret);
    }
}