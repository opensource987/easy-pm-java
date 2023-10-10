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
 * @ClassName SysRoleVo
 * @Author mdframework
 * @Date 2021-02-02
 * @Version 1.0.0
 * @Description 角色信息Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysRoleVo {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @ApiModelProperty("角色ID")
    private Long roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    @ApiModelProperty("角色名称")
    private String roleName;

    /** 角色权限字符串 */
    @Excel(name = "角色权限字符串")
    @ApiModelProperty("角色权限字符串")
    private String roleKey;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer roleSort;

    /** 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限） */
    @Excel(name = "数据范围", readConverterExp = "1=：全部数据权限,2=：自定数据权限,3=：本部门数据权限,4=：本部门及以下数据权限")
    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private String dataScope;

    /** 菜单树选择项是否关联显示 */
    @Excel(name = "菜单树选择项是否关联显示")
    @ApiModelProperty("菜单树选择项是否关联显示")
    private Integer menuCheckStrictly;

    /** 部门树选择项是否关联显示 */
    @Excel(name = "部门树选择项是否关联显示")
    @ApiModelProperty("部门树选择项是否关联显示")
    private Integer deptCheckStrictly;

    /** 角色状态（0正常 1停用） */
    @Excel(name = "角色状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("角色状态（0正常 1停用）")
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

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String remark;

}
