package com.mdframework.module.member.domain.vo;

import com.mdframework.common.annotation.Excel;
import com.mdframework.common.core.domain.BasePlusEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName MemberInfo
 * @Author mdframework
 * @Date 2020-11-06
 * @Version 1.0.0
 * @Description 会员信息对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class MemberInfoVo extends BasePlusEntity {
    private static final long serialVersionUID = 1L;

    /** 会员ID */
    @ApiModelProperty("会员ID")
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 昵称 */
    @Excel(name = "昵称")
    @ApiModelProperty("昵称")
    private String nickname;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;

    /** 性别 */
    @Excel(name = "性别")
    @ApiModelProperty("性别")
    private Integer sex;

    /** 头像 */
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String pic;

    /** 来源 */
    @Excel(name = "来源")
    @ApiModelProperty("来源")
    private Integer source;

    /** 小程序openId */
    @Excel(name = "小程序openId")
    @ApiModelProperty("小程序openId")
    private String wxMiniOpenId;

    /** 微信unionId */
    @Excel(name = "微信unionId")
    @ApiModelProperty("微信unionId")
    private String wxUnionId;

    /** 备注 */
    @ApiModelProperty("备注")
    private String remark;

}
