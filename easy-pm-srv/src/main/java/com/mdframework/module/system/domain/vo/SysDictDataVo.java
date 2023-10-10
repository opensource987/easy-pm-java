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
 * @ClassName SysDictDataVo
 * @Author mdframework
 * @Date 2021-02-02
 * @Version 1.0.0
 * @Description 字典数据Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysDictDataVo {
    private static final long serialVersionUID = 1L;

    /** 字典编码 */
    @ApiModelProperty("字典编码")
    private Long dictCode;

    /** 字典排序 */
    @Excel(name = "字典排序")
    @ApiModelProperty("字典排序")
    private Integer dictSort;

    /** 字典标签 */
    @Excel(name = "字典标签")
    @ApiModelProperty("字典标签")
    private String dictLabel;

    /** 字典键值 */
    @Excel(name = "字典键值")
    @ApiModelProperty("字典键值")
    private String dictValue;

    /** 字典类型 */
    @Excel(name = "字典类型")
    @ApiModelProperty("字典类型")
    private String dictType;

    /** 样式属性（其他样式扩展） */
    @Excel(name = "样式属性", readConverterExp = "其=他样式扩展")
    @ApiModelProperty("样式属性（其他样式扩展）")
    private String cssClass;

    /** 表格回显样式 */
    @Excel(name = "表格回显样式")
    @ApiModelProperty("表格回显样式")
    private String listClass;

    /** 是否默认（Y是 N否） */
    @Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
    @ApiModelProperty("是否默认（Y是 N否）")
    private String isDefault;

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
