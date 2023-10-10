package com.mdframework.module.project.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdframework.module.system.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.mdframework.common.annotation.Excel;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectModuleVo
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目模块Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectModuleVo {
    private static final long serialVersionUID = 1L;

    /** 自增id */
    @ApiModelProperty("自增id")
    private Long id;

    /** 模块名 */
    @Excel(name = "模块名")
    @ApiModelProperty("模块名")
    private String moduleName;

    /** 模块名 */
    @Excel(name = "父模块名")
    @ApiModelProperty("父级模块名")
    @TableField(exist = false)
    private String parentModuleName;

    /** 父级模块名 */
    @Excel(name = "父级模块id")
    @ApiModelProperty("父级模块id")
    private Long parentId;

    /** 模块进度 */
    @Excel(name = "模块进度")
    @ApiModelProperty("模块进度")
    private Integer progress;

    /** 关联项目id */
    @Excel(name = "关联项目id")
    @ApiModelProperty("关联项目id")
    private Long projectId;

    /** 关联项目名 */
    @Excel(name = "关联项目名")
    @ApiModelProperty("关联项目名")
    private String projectName;

    @ApiModelProperty("项目金额")
    private BigDecimal amount;

    //项目绩效分
    @TableField(exist = false)
    private Double projectPoint;

    /** 模块启动时间 */
    @Excel(name = "模块启动时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("模块启动时间")
    private Date moduleBeginTime;

    /** 模块预计结束时间 */
    @Excel(name = "模块预计结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("模块预计结束时间")
    private Date modulePredictEndTime;

//    @TableField(exist = false)
//    private Date defaultBeginTime;
//
//    @TableField(exist = false)
//    private Date defaultEndTime;


    /** 0为正常，1为暂停，2为完成，3为取消 */
    @Excel(name = "0为正常，1为暂停，2为完成，3为取消")
    @ApiModelProperty("0为正常，1为暂停，2为完成，3为取消")
    private Integer status;

    //模块关联用户
    @TableField(exist = false)
    private List<SysUser> users;

    /** 备注 */
    @Excel(name = "备注")
    @ApiModelProperty("备注")
    private String remark;

    /** 排序 */
    private Long orderNum;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delStatus;

}
