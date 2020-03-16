package com.hellozjf.wxgzh;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import lombok.extern.slf4j.Slf4j;
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
        String token = "hellozjf123456";

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
        if (document.getElementsByTagName("MsgType").item(0).getTextContent().equalsIgnoreCase("text")) {
            String toUser = document.getElementsByTagName("ToUserName").item(0).getTextContent();
            String fromUser = document.getElementsByTagName("FromUserName").item(0).getTextContent();
            String content = "test";
            // todo 这个response还有问题，没有<CDATA>标签
            String response = getResponse(toUser, fromUser, content);
            log.debug("response = {}", response);
            return response;
        } else {
            log.debug("暂且不处理");
            return "success";
        }
    }

    private String getResponse(String toUser, String fromUser, String content) {
        Document document = XmlUtil.createXml("xml");

        Element toUserNameElement = document.createElement("ToUserName");
        toUserNameElement.setTextContent(toUser);
        document.getDocumentElement().appendChild(toUserNameElement);

        Element fromUserNameElement = document.createElement("FromUserName");
        fromUserNameElement.setTextContent(fromUser);
        document.getDocumentElement().appendChild(fromUserNameElement);

        Element createTimeElement = document.createElement("CreateTime");
        createTimeElement.setTextContent(String.valueOf(DateUtil.currentSeconds()));
        document.getDocumentElement().appendChild(createTimeElement);

        Element msgTypeElement = document.createElement("MsgType");
        msgTypeElement.setTextContent("text");
        document.getDocumentElement().appendChild(msgTypeElement);

        Element contentElement = document.createElement("Content");
        contentElement.setTextContent(content);
        document.getDocumentElement().appendChild(contentElement);

        return XmlUtil.format(document);
    }
}
