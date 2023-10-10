package com.mdframework.common.constant;

import com.mdframework.common.utils.ip.IpUtils;

/**
 * 通用常量信息
 * 
 * @author mdframework
 */
public class Constants
{
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = getProjectDirName()+"captcha_codes:";

    /**
     * 短信验证码 redis key
     */
    public static final String SMS_CODE_KEY = getProjectDirName()+"sms_codes:";

    /**
     * 短信验证码 redis key
     */
    public static final String AlREADY_SEND_SMS_CODE_KEY = getProjectDirName()+"already_send_sms_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = getProjectDirName()+"login_tokens:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 短信验证码有效期（分钟）
     */
    public static final Integer SMS_EXPIRATION = 5;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = "sub";

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = getProjectDirName()+"sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = getProjectDirName()+"sys_dict:";

    public static String ProjectModulesKeyPrefix = getProjectDirName()+"-ProjectModulesKey";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    private static String getProjectDirName() {
        String dir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        int i = dir.lastIndexOf(separator);
        dir = dir.substring(i + 1);

        //在linux环境可能拿不到user.dir的情况，则拿计算机名
        if(dir==null || dir.equals("")){
            System.out.println("IpUtils.getHostName()="+IpUtils.getHostName());
            String hostName = IpUtils.getHostName();
            i = hostName.lastIndexOf("-",hostName.lastIndexOf("-")-1);
            if(i>0){
                hostName = hostName.substring(0,i);
            }
            dir = hostName + "-";
        }
        System.out.println("getProjectDirName="+dir);
        return dir;
    }

    public static void main(String[] args){
        String name = "aa-q8p6w";
        int i = name.lastIndexOf("-",name.lastIndexOf("-")-1);
        name = name.substring(0,i);
        System.out.println(name);
    }
}
