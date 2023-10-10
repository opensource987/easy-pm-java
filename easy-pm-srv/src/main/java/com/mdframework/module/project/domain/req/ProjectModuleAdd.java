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
 * @ClassName ProjectModuleAdd
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目模块Add对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectModuleAdd {
    private static final long serialVersionUID = 1L;

    /** 模块名 */
    @ApiModelProperty("模块名")
    private String moduleName;

    /** 父级模块名 */
    @ApiModelProperty("父级模块名")
    private Long parentId;

    /** 模块进度 */
    @ApiModelProperty("模块进度")
    private Integer progress;

    /** 关联项目id */
    @ApiModelProperty("关联项目id")
    private Long projectId;

    /** 模块启动时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("模块启动时间")
    private Date moduleBeginTime;

    /** 模块预计结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("模块预计结束时间")
    private Date modulePredictEndTime;

    /** 0为正常，1为暂停，2为完成，3为取消 */
    @ApiModelProperty("0为正常，1为暂停，2为完成，3为取消")
    private Integer status;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

    /** 删除标志（0代表存在 2代表删除） */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delStatus;

}
