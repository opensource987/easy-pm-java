package com.mdframework.message;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
阿里大于短信发送
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.message")
public class AliyunMessage {

    private String accessKeyId;
    private String accessSecret;
    private String domain;
    private String signName;
    private String templateCode;

    /**
     * 批量发送通知短信
     * @return
     */
    public Map<String,Object> sendMessageBatch(String templateCode, List<String> mobileList, List<Map<String,Object>> param) {
        DefaultProfile profile = DefaultProfile.getProfile("default", this.accessKeyId,
                this.accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(this.domain);
        request.setVersion("2017-05-25");
        request.setAction("SendBatchSms");
        request.putQueryParameter("PhoneNumberJson", JSONUtil.toJsonStr(mobileList));
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParamJson", JSONUtil.toJsonStr(param));

        List<String> signNameList = new ArrayList<>();
        for (String temp : mobileList) {
            signNameList.add((this.signName));
        }
        request.putQueryParameter("SignNameJson", JSONUtil.toJsonStr(signNameList));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if (response.getHttpResponse().isSuccess()) {
                Map<String,Object> map = JSON.parseObject(response.getData(), Map.class);
                if(map.get("Code").equals("isv.BUSINESS_LIMIT_CONTROL")){
                    return map;
                }
                return map;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 发送阿里大鱼短信
     * @return
     */
    public Map<String,Object> sendMessage(String templateCode,String mobile, Map<String,Object> param) {
        DefaultProfile profile = DefaultProfile.getProfile("default", this.accessKeyId,
                this.accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(this.domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(param));
        request.putQueryParameter("SignName", this.signName);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if (response.getHttpResponse().isSuccess()) {
                Map<String,Object> map = JSON.parseObject(response.getData(), Map.class);
                if(map.get("Code").equals("isv.BUSINESS_LIMIT_CONTROL")){
                    return map;
                }
                return map;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送阿里大鱼验证码短信
     * @return
     */
    public boolean alidayuCheckCode(String mobile,String code) {
        DefaultProfile profile = DefaultProfile.getProfile("default", this.accessKeyId,
                this.accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain(this.domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("TemplateCode", templateCode);
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        request.putQueryParameter("TemplateParam", JSONUtil.toJsonStr(param));
        request.putQueryParameter("SignName", this.signName);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            if (response.getHttpResponse().isSuccess()) {
                Map map = JSON.parseObject(response.getData(), Map.class);
                if(map.get("Code").equals("isv.BUSINESS_LIMIT_CONTROL")){
                    return false;
                }
                return true;
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        AliyunMessage commonRpc = new AliyunMessage();
        commonRpc.accessKeyId = "LTAI4G2jMxZexMMM59haTuGu";
        commonRpc.accessSecret = "J6DAYsOiLaCDZ2il0NZN9hv1SURbrD";
        commonRpc.signName = "德爱健康";
        commonRpc.templateCode = "SMS_193027088";
        commonRpc.domain = "dysmsapi.aliyuncs.com";
        System.out.println(commonRpc.alidayuCheckCode("15889963910","1234"));


    }
}