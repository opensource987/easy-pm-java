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
 * @ClassName SysUserVo
 * @Author mdframework
 * @Date 2021-02-01
 * @Version 1.0.0
 * @Description 用户信息Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysUserVo {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty("用户ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    @ApiModelProperty("部门ID")
    private Long deptId;

    /** 用户账号 */
    @Excel(name = "用户账号")
    @ApiModelProperty("用户账号")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户昵称")
    @ApiModelProperty("用户昵称")
    private String nickName;

    /** 用户类型（00系统用户） */
    @Excel(name = "用户类型", readConverterExp = "0=0系统用户")
    @ApiModelProperty("用户类型（00系统用户）")
    private String userType;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    @ApiModelProperty("用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /** 用户性别（0男 1女 2未知） */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    /** 头像地址 */
    @Excel(name = "头像地址")
    @ApiModelProperty("头像地址")
    private String avatar;

    /** 密码 */
    @Excel(name = "密码")
    @ApiModelProperty("密码")
    private String password;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /** 最后登录IP */
    @Excel(name = "最后登录IP")
    @ApiModelProperty("最后登录IP")
    private String loginIp;

    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    /** 创建者 */
    @ApiModelProperty("创建者")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新者 */
    @ApiModelProperty("更新者")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String remark;

}
