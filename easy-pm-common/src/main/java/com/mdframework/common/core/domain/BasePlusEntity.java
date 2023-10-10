package com.mdframework.common.core.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 *
 * @author Jesse 573110463@qq.com
 */
@Data
public class BasePlusEntity implements Serializable {
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

   /* public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }*/
    @JsonIgnore
    @TableField(exist=false)
    private Date beginTime;

    @JsonIgnore
    @TableField(exist=false)
    private Date endTime;

}
