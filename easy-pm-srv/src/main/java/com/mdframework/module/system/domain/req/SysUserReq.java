package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.base.domain.req.BaseReq;
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
 * @ClassName SysUserReq
 * @Author mdframework
 * @Date 2021-02-01
 * @Version 1.0.0
 * @Description 列表查询信息体
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysUserReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("名称")
     private String userName;

     @ApiModelProperty("帐号状态")
     private String status;

     @ApiModelProperty("手机号码")
     private String phonenumber;

     @ApiModelProperty("部门编号")
     private String deptId;

    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<SysUser> lqw) {
        String alias = "o.";
        lqw.eq(alias+"del_flag","0");
        if (StringUtils.isNotBlank(this.getUserName()) && this.getUserName() != null) {
            lqw.eq(  alias+"user_name", this.getUserName());
        }
        if (StringUtils.isNotBlank(this.getStatus()) && this.getStatus() != null) {
            lqw.eq(  alias+"status", this.getStatus());
        }
        if (StringUtils.isNotBlank(this.getPhonenumber()) && this.getPhonenumber() != null) {
            lqw.eq(  alias+"phonenumber", this.getPhonenumber());
        }
        if (StringUtils.isNotBlank(this.getDeptId()) && this.getDeptId() != null) {
            lqw.eq( alias+"dept_id", this.getDeptId());
        }
    }
}
