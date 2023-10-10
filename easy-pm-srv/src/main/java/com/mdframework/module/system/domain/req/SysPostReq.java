package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.annotation.Excel;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.system.domain.SysPost;
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
 * @ClassName SysPostReq
 * @Author mdframework
 * @Date 2021-02-08
 * @Version 1.0.0
 * @Description 列表查询信息体
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysPostReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位编码")
    private String postCode;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;
    @ApiModelProperty(value = "岗位名称")
    private String postName;
    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<SysPost> lqw) {
        String alias = "o.";
        if(StringUtils.isNotBlank(this.getPostCode())){
            lqw.eq(alias+"post_code",this.getPostCode());
        }
        if(StringUtils.isNotBlank(this.getStatus())){
            lqw.eq(alias+"status",this.getStatus());
        }
        if(StringUtils.isNotBlank(this.getPostName())){
            lqw.eq(alias+"post_name",this.getPostName());
        }
    }
}
