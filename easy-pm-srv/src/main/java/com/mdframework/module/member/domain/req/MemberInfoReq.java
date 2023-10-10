package com.mdframework.module.member.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.annotation.Excel;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.member.domain.MemberInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName MemberInfoReq
 * @Author mdframework
 * @Date 2020-11-06
 * @Version 1.0.0
 * @Description 列表查询信息体
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class MemberInfoReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("名称")
     private String name;
     @ApiModelProperty("手机号码")
     private String mobile;

    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Return void
    */
    public void generatorQuery(QueryWrapper<MemberInfo> lqw) {
        if (StringUtils.isNotBlank(this.getName())){
            lqw.like("name",this.getName());
        }
        if (StringUtils.isNotBlank(this.getMobile())){
            lqw.like("mobile",this.getMobile());
        }
    }
}
