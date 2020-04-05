package com.hellozjf.wxgzh.service;

import com.hellozjf.wxgzh.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author Jingfeng Zhou
 */
@Slf4j
public class MenuServiceTest extends BaseTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private MenuService menuService;

    @Test
    public void create() {
        String accessToken = "32_Swfv0zC5HQDHRzRg6WgvC9iNnQJwm6E_TFngYSUlJn-p01OoaYcQUCzMN015gKVby7HS4FEsw7phGhfLlJ_A6EXm5FIQ_g9bjs1nIrRHN54Kh_9LcXbiGN88kzDfBlaPMlMQTqQ_4ocOwd4NYVHgADABYK";
        String menu = "{\n" +
                "        \"button\":\n" +
                "        [\n" +
                "            {\n" +
                "                \"type\": \"click\",\n" +
                "                \"name\": \"开发指引\",\n" +
                "                \"key\":  \"mpGuide\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"公众平台\",\n" +
                "                \"sub_button\":\n" +
                "                [\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"更新公告\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1418702138&token=&lang=zh_CN\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"接口权限说明\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1418702138&token=&lang=zh_CN\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                        \"type\": \"view\",\n" +
                "                        \"name\": \"返回码说明\",\n" +
                "                        \"url\": \"http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433747234&token=&lang=zh_CN\"\n" +
                "                    }\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"type\": \"media_id\",\n" +
                "                \"name\": \"旅行\",\n" +
                "                \"media_id\": \"z2zOokJvlzCXXNhSjF46gdx6rSghwX2xOD5GUV9nbX4\"\n" +
                "            }\n" +
                "          ]\n" +
                "    }";
        String result = menuService.create(accessToken, menu);
        log.debug("result = {}", result);
    }
}