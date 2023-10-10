package com.mdframework.module.project.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdframework.module.system.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.mdframework.common.core.domain.BasePlusEntity;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName Project
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目信息 PO对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("epm_project")
public class Project extends BasePlusEntity {
    private static final long serialVersionUID = 1L;

    /** 项目id，自增 */
    private Long id;
    /** 项目名 */
    private String projectName;
    /** 项目类型，0为内部项目，1为外部项目 */
    private Integer projectType;
    /** 项目启动时间 */
    private Date projectBeginTime;
    /** 项目预计结束时间 */
    private Date projectPredictEndTime;
    /** 项目进度 */
    private String progress;

    private BigDecimal amount;

    /** 0为正常，1为暂停，2为完成，3为取消 */
    private Integer status;

    //项目关联用户
    @TableField(exist = false)
    private List<SysUser> users;

    //项目关联模块
    @TableField(exist = false)
    private List<ProjectModule> modules;

    private Long orderNum;
    /** 备注 */
    private String remark;
    /** 删除标志（0代表存在 2代表删除） */
//    private String delFlag;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "close_Time")
    private Date closeTime;
}
