package com.mdframework.module.system.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdframework.common.core.domain.BasePlusEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Jesse
 */
@Data
public class TreePlusEntity extends BasePlusEntity {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @TableLogic
    @ApiModelProperty(hidden = true)
    private Integer delStatus;
    /**
     * 创建者
     * 新增执行
     */
    @TableField(value = "create_by" , fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time" , fill = FieldFill.INSERT)
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新者
     * 新增和更新执行
     */
    @TableField(value = "update_by" , fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(hidden = true)
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_Time" , fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonIgnore
    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    private Map<String, Object> params;
//    /**
//     * 父菜单名称
//     */
//    private String parentName;

    /**
     * 祖级列表
     */
    private String ancestors;

    /**
     * 子部门
     */
    @TableField(exist = false)
    private List<?> children = new ArrayList<>();

}
