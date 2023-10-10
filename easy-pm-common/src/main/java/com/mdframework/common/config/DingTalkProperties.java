package com.mdframework.common.config;

import com.mdframework.common.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dingtalk")
public class DingTalkProperties {

  private String apiUrl;
  private String corpId;
  private String agentId;
  private String appKey;
  private String appsecret;
  private String aesKey;
  private String token;




  @Override
  public String toString() {
    return JsonUtils.toJson(this);
  }
}
