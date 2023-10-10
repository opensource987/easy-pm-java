package com.mdframework.common.config;

import com.google.common.collect.Maps;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Configuration
@EnableConfigurationProperties(WxCpProperties.class)
public class WxCpConfiguration {

    private WxCpProperties properties;
    private static WxCpService cpService;
    private static String wxcpQrCodeUrl;
    private  static final String wxUrl="https://open.work.weixin.qq.com/wwopen/sso/qrConnect";

    public static WxCpService getCpService() {
        return cpService;
    }

    public static String getWxcpQrCodeUrl(){
        return wxcpQrCodeUrl;
    }

    @Autowired
    public WxCpConfiguration(WxCpProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void initService() {
        val configStorage = new WxCpDefaultConfigImpl();
        configStorage.setCorpId(properties.getCorpId());
        configStorage.setAgentId(properties.getAgentId());
        configStorage.setCorpSecret(properties.getSecret());
        configStorage.setToken(properties.getToken());
        configStorage.setAesKey(properties.getAesKey());
        cpService = new WxCpServiceImpl();
        cpService.setWxCpConfigStorage(configStorage);

        StringBuffer url=new StringBuffer(wxUrl).append("?appid=").append(properties.getCorpId()).append("&agentid=").append(properties.getAgentId()).append("&redirect_uri=");
        try {
            url.append(properties.getCallBackUrl());
        }catch(Exception e){

        }
        wxcpQrCodeUrl = url.toString();
    }


}
