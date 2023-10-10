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
 * @ClassName SysDeptVo
 * @Author mdframework
 * @Date 2021-02-02
 * @Version 1.0.0
 * @Description 部门Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysDeptVo {
    private static final long serialVersionUID = 1L;

    /** 部门id */
    @ApiModelProperty("部门id")
    private Long deptId;

    /** 父部门id */
    @Excel(name = "父部门id")
    @ApiModelProperty("父部门id")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    @ApiModelProperty("祖级列表")
    private String ancestors;

    /** 部门名称 */
    @Excel(name = "部门名称")
    @ApiModelProperty("部门名称")
    private String deptName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /** 负责人 */
    @Excel(name = "负责人")
    @ApiModelProperty("负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ApiModelProperty("联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty("邮箱")
    private String email;

    /** 部门状态（0正常 1停用） */
    @Excel(name = "部门状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("部门状态（0正常 1停用）")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

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

    /** 企业微信部门id */
    @ApiModelProperty("企业微信部门id")
    private Long wxcpDeptId;

    /** 企业微信父部门id */
    @Excel(name = "企业微信父部门id")
    @ApiModelProperty("企业微信父部门id")
    private Long wxcpParentId;

}
