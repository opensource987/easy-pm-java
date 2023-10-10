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
 * @ClassName SysPostVo
 * @Author mdframework
 * @Date 2021-02-08
 * @Version 1.0.0
 * @Description 岗位信息Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysPostVo {
    private static final long serialVersionUID = 1L;

    /** 岗位ID */
    @ApiModelProperty("岗位ID")
    private Long postId;

    /** 岗位编码 */
    @Excel(name = "岗位编码")
    @ApiModelProperty("岗位编码")
    private String postCode;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    @ApiModelProperty("岗位名称")
    private String postName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer postSort;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty("状态（0正常 1停用）")
    private String status;

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
