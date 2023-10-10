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
 * @ClassName SysConfigVo
 * @Author mdframework
 * @Date 2021-02-08
 * @Version 1.0.0
 * @Description 参数配置Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysConfigVo {
    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    @ApiModelProperty("参数主键")
    private Integer configId;

    /** 参数名称 */
    @Excel(name = "参数名称")
    @ApiModelProperty("参数名称")
    private String configName;

    /** 参数键名 */
    @Excel(name = "参数键名")
    @ApiModelProperty("参数键名")
    private String configKey;

    /** 参数键值 */
    @Excel(name = "参数键值")
    @ApiModelProperty("参数键值")
    private String configValue;

    /** 系统内置（Y是 N否） */
    @Excel(name = "系统内置", readConverterExp = "Y=是,N=否")
    @ApiModelProperty("系统内置（Y是 N否）")
    private String configType;

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
