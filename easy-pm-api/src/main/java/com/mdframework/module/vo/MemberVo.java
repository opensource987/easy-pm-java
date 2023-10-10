package com.mdframework.module.vo;

import com.mdframework.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 会员下发体
 * @author kerry
 */
@Data
@ApiModel
public class MemberVo {


 /** 会员ID */
 @ApiModelProperty("会员ID")
 private Long id;

 /** 名称 */
 @Excel(name = "名称")
 @NotBlank(message = "请输入名称")
 @ApiModelProperty("名称")
 private String name;

 /** 昵称 */
 @Excel(name = "昵称")
 @ApiModelProperty("昵称")
 private String nickname;

 /** 手机号码 */
 @Excel(name = "手机号码")
 @NotBlank(message = "请输入手机号码")
 @ApiModelProperty("手机号码")
 private String mobile;

 /** 性别 */
 @Excel(name = "性别")
 @NotNull(message = "请输入性别")
 @ApiModelProperty("性别")
 private Integer sex;

 /** 头像 */
 @Excel(name = "头像")
 @NotBlank(message = "请输入头像")
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

  /** 登录token */
  @ApiModelProperty("登录token")
  private String token;

}
