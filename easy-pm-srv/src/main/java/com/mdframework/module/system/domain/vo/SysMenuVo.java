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
 * @ClassName SysMenuVo
 * @Author mdframework
 * @Date 2021-02-02
 * @Version 1.0.0
 * @Description 菜单权限Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysMenuVo {
    private static final long serialVersionUID = 1L;

    /** 菜单ID */
    @ApiModelProperty("菜单ID")
    private Long menuId;

    /** 菜单名称 */
    @Excel(name = "菜单名称")
    @ApiModelProperty("菜单名称")
    private String menuName;

    /** 父菜单ID */
    @Excel(name = "父菜单ID")
    @ApiModelProperty("父菜单ID")
    private Long parentId;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /** 路由地址 */
    @Excel(name = "路由地址")
    @ApiModelProperty("路由地址")
    private String path;

    /** 组件路径 */
    @Excel(name = "组件路径")
    @ApiModelProperty("组件路径")
    private String component;

    /** 是否为外链（0是 1否） */
    @Excel(name = "是否为外链", readConverterExp = "0=是,1=否")
    @ApiModelProperty("是否为外链（0是 1否）")
    private Integer isFrame;

    /** 是否缓存（0缓存 1不缓存） */
    @Excel(name = "是否缓存", readConverterExp = "0=缓存,1=不缓存")
    @ApiModelProperty("是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    /** 菜单类型（M目录 C菜单 F按钮） */
    @Excel(name = "菜单类型", readConverterExp = "M=目录,C=菜单,F=按钮")
    @ApiModelProperty("菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    /** 菜单状态（0显示 1隐藏） */
    @Excel(name = "菜单状态", readConverterExp = "0=显示,1=隐藏")
    @ApiModelProperty("菜单状态（0显示 1隐藏）")
    private String visible;

    /** 菜单状态（0正常 1停用） */
    @Excel(name = "菜单状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("菜单状态（0正常 1停用）")
    private String status;

    /** 权限标识 */
    @Excel(name = "权限标识")
    @ApiModelProperty("权限标识")
    private String perms;

    /** 菜单图标 */
    @Excel(name = "菜单图标")
    @ApiModelProperty("菜单图标")
    private String icon;

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
