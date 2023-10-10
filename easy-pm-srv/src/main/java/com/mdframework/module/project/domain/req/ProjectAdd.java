package com.mdframework.module.project.domain.req;

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
 * @ClassName ProjectAdd
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目信息Add对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectAdd {
    private static final long serialVersionUID = 1L;

    /** 项目名 */
    @ApiModelProperty("项目名")
    private String projectName;

    /** 项目类型，0为内部项目，1为外部项目 */
    @ApiModelProperty("项目类型，0为内部项目，1为外部项目")
    private Integer projectType;

    /** 项目启动时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("项目启动时间")
    private Date projectBeginTime;

    /** 项目预计结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("项目预计结束时间")
    private Date projectPredictEndTime;

    /** 项目进度 */
    @ApiModelProperty("项目进度")
    private Integer progress;

    /** 项目金额 */
    @ApiModelProperty("项目金额")
    private BigDecimal amount;

    /** 0为正常，1为暂停，2为完成，3为取消 */
    @ApiModelProperty("0为正常，1为暂停，2为完结，3为取消，4为运营，5为售前")
    private Integer status;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delStatus;

}
