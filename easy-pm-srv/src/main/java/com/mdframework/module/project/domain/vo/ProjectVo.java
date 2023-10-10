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
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectVo
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目信息Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectVo implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 项目id，自增 */
    @ApiModelProperty("项目id")
    private Long id;

    /** 项目名 */
    @Excel(name = "项目名")
    @ApiModelProperty("项目名")
    private String projectName;

    /** 项目类型，0为内部项目，1为外部项目 */
    @Excel(name = "项目类型")
    @ApiModelProperty("项目类型，0为内部项目，1为外部项目")
    private Integer projectType;

    /** 项目启动时间 */
    @Excel(name = "项目启动时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("项目启动时间")
    private Date projectBeginTime;

    /** 项目预计结束时间 */
    @Excel(name = "项目预计结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("项目预计结束时间")
    private Date projectPredictEndTime;

    /** 项目进度 */
    @Excel(name = "项目进度")
    @ApiModelProperty("项目进度")
    private Integer progress;

    /** 0为正常，1为暂停，2为完成，3为取消 */
    @Excel(name = "0为正常，1为暂停，2为完成，3为取消")
    @ApiModelProperty("0为正常，1为暂停，2为完成，3为取消")
    private Integer status;

    /** 项目金额 */
    @Excel(name = "项目金额")
    @ApiModelProperty("项目金额")
    private BigDecimal amount;

    //项目绩效分
    @TableField(exist = false)
    private Double projectPoint;

    //项目关联用户
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

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("完结时间")
    private Date closeTime;

    /** 删除标志（0代表存在 2代表删除） */
    @Excel(name = "删除标志", readConverterExp = "0=代表存在,2=代表删除")
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectVo projectVo = (ProjectVo) o;
        return id.equals(projectVo.id) && projectName.equals(projectVo.projectName) && delStatus.equals(projectVo.delStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectName, delStatus);
    }
}
