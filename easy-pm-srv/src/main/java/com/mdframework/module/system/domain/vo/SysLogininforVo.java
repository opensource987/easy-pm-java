package com.mdframework.module.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.mdframework.common.annotation.Excel;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.validation.constraints.*;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName SysLogininforVo
 * @Author mdframework
 * @Date 2021-02-18
 * @Version 1.0.0
 * @Description 系统访问记录Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysLogininforVo {
    private static final long serialVersionUID = 1L;

    /** 访问ID */
    @ApiModelProperty("访问ID")
    private Long infoId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    @ApiModelProperty("用户账号")
    private String userName;

    /** 登录IP地址 */
    @Excel(name = "登录IP地址")
    @ApiModelProperty("登录IP地址")
    private String ipaddr;

    /** 登录地点 */
    @Excel(name = "登录地点")
    @ApiModelProperty("登录地点")
    private String loginLocation;

    /** 浏览器类型 */
    @Excel(name = "浏览器类型")
    @ApiModelProperty("浏览器类型")
    private String browser;

    /** 操作系统 */
    @Excel(name = "操作系统")
    @ApiModelProperty("操作系统")
    private String os;

    /** 登录状态（0成功 1失败） */
    @Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
    @ApiModelProperty("登录状态（0成功 1失败）")
    private String status;

    /** 提示消息 */
    @Excel(name = "提示消息")
    @ApiModelProperty("提示消息")
    private String msg;

    /** 访问时间 */
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("访问时间")
    private Date loginTime;

}
