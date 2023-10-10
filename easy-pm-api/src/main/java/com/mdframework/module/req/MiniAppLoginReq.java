package com.mdframework.module.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * 授权登录请求体
 */
@Data
@ApiModel
@Validated
public class MiniAppLoginReq {

    @ApiModelProperty(name="code",value = "code-必填",notes = "code", required = true, dataType = "String")
    @NotBlank(message = "code不能为空")
    protected String code;

    @ApiModelProperty(name="iv",value = "iv-必填",notes = "iv", required = true, dataType = "String")
    @NotBlank(message = "iv不能为空")
    protected String iv;

    @ApiModelProperty(name="encryptedData",value = "encryptedData-必填",notes = "encryptedData", required = true, dataType = "String")
    @NotBlank(message = "encryptedData不能为空")
    protected String encryptedData;

    @ApiModelProperty(name="rawData",value = "调用wx.getUserProfile返回的rawData值",notes = "rawData", dataType = "String")
    protected String rawData;

}
