package com.mdframework.module.system.domain.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登录对象
 * 
 * @author mdframework
 */
public class LoginBody
{
    /**
     * 用户名
     */
    @ApiModelProperty(value = "账号",example = "admin",required = true)
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码",example = "987!aDmin",required = true)
    private String password;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码",example = "",required = true)
    private String code;

    /**
     * 唯一标识
     */
    @ApiModelProperty(value = "验证码唯一标识，调用获取验证码接口会下发这个值",example = "adjhkjdhjaajhjjk",required = true)
    private String uuid = "";

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
