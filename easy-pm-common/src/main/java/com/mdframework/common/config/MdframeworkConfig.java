package com.mdframework.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author mdframework
 */
@Component
@ConfigurationProperties(prefix = "mdframework")
public class MdframeworkConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /**验证码类型*/
    private static String captchaType;

    /** 上传路径 */
    private static String profile;

    /** 图片前缀路径 */
    private static String prePath;

    public static String getPrePath() {
        return prePath;
    }

    public void setPrePath(String prePath) {
        MdframeworkConfig.prePath = prePath;
    }

    /** 获取地址开关 */
    private static boolean addressEnabled;

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        MdframeworkConfig.captchaType = captchaType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }

    public void setDemoEnabled(boolean demoEnabled)
    {
        this.demoEnabled = demoEnabled;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        MdframeworkConfig.profile = profile;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        MdframeworkConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
