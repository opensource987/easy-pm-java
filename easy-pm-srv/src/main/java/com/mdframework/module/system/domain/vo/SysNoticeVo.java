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
 * @ClassName SysNoticeVo
 * @Author mdframework
 * @Date 2021-02-08
 * @Version 1.0.0
 * @Description 通知公告Vo对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysNoticeVo {
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    @ApiModelProperty("公告ID")
    private Integer noticeId;

    /** 公告标题 */
    @Excel(name = "公告标题")
    @ApiModelProperty("公告标题")
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    @Excel(name = "公告类型", readConverterExp = "1=通知,2=公告")
    @ApiModelProperty("公告类型（1通知 2公告）")
    private String noticeType;

    /** 公告内容 */
    @Excel(name = "公告内容")
    @ApiModelProperty("公告内容")
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    @Excel(name = "公告状态", readConverterExp = "0=正常,1=关闭")
    @ApiModelProperty("公告状态（0正常 1关闭）")
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
