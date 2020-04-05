package com.hellozjf.wxgzh.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class WxgzhController {

    @GetMapping("/wx")
    public String getWx(String signature, String timestamp, String nonce, String echostr) throws UnsupportedEncodingException {
        String token = "sosjpeudrm47zcxiwuc0axtugffzdzid";

        List<String> list = Arrays.asList(token, timestamp, nonce);
        Collections.sort(list);

        Digester sha1 = new Digester(DigestAlgorithm.SHA1);
        for (String s : list) {
            sha1.getDigest().update(s.getBytes("UTF-8"));
        }
        String hashcode = HexUtil.encodeHexStr(sha1.getDigest().digest());
        log.debug("handle/GET func: hashcode, signature: {} {}", hashcode, signature);

        if (hashcode.equalsIgnoreCase(signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    @PostMapping("/wx")
    public String postWx(@RequestBody String data) {
        log.debug("Handle Post webdata is {}", data);
        // 后台打日志
        Document document = XmlUtil.parseXml(data);
        // 这里toUser和fromUser要反一下
        String fromUser = document.getElementsByTagName("ToUserName").item(0).getTextContent();
        String toUser = document.getElementsByTagName("FromUserName").item(0).getTextContent();
        String msgType = document.getElementsByTagName("MsgType").item(0).getTextContent();
        if (msgType.equalsIgnoreCase("text")) {
            String content = document.getElementsByTagName("Content").item(0).getTextContent();
            String response = getTextResponse(toUser, fromUser, content);
            log.debug("response = {}", response);
            return response;
        } else if (msgType.equalsIgnoreCase("image")) {
            String picUrl = document.getElementsByTagName("PicUrl").item(0).getTextContent();
            String mediaId = document.getElementsByTagName("MediaId").item(0).getTextContent();
            String response = getImageResponse(toUser, fromUser, picUrl, mediaId);
            log.debug("response = {}", response);
            return response;
        } else {
            log.debug("暂且不处理");
            return "success";
        }
    }

    /**
     * 获取图片处理结果
     * @param toUser
     * @param fromUser
     * @param picUrl
     * @param mediaId
     * @return
     */
    private String getImageResponse(String toUser, String fromUser, String picUrl, String mediaId) {
        String xml = "<xml>\n" +
                "                <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "                <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "                <CreateTime>%d</CreateTime>\n" +
                "                <MsgType><![CDATA[image]]></MsgType>\n" +
                "                <Image>\n" +
                "                <MediaId><![CDATA[%s]]></MediaId>\n" +
                "                </Image>\n" +
                "            </xml>";
        return String.format(xml, toUser, fromUser, DateUtil.currentSeconds(), mediaId);
    }

    /**
     * 获取文本处理结果
     * @param toUser
     * @param fromUser
     * @param content
     * @return
     */
    private String getTextResponse(String toUser, String fromUser, String content) {
        String xml = "            <xml>\n" +
                "                <ToUserName><![CDATA[%s]]></ToUserName>\n" +
                "                <FromUserName><![CDATA[%s]]></FromUserName>\n" +
                "                <CreateTime>%d</CreateTime>\n" +
                "                <MsgType><![CDATA[text]]></MsgType>\n" +
                "                <Content><![CDATA[%s]]></Content>\n" +
                "            </xml>";
        return String.format(xml, toUser, fromUser, DateUtil.currentSeconds(), content);
    }
}
