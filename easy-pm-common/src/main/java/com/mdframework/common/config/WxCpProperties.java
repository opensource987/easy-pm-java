package com.mdframework.common.config;

import com.mdframework.common.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "wechat.cp")
public class WxCpProperties {

  /**
   * 设置企业微信的corpId
   */
  private String corpId;

  /**
   * 设置企业微信应用的AgentId
   */
  private Integer agentId;

  /**
   * 设置企业微信应用的Secret
   */
  private String secret;

  /**
   * 设置企业微信应用的token
   */
  private String token;

  /**
   * 设置企业微信应用的EncodingAESKey
   */
  private String aesKey;

  private String callBackUrl;

  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
}
