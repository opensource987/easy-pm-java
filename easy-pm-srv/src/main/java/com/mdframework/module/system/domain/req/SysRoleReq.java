package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.system.domain.entity.SysRole;
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
 * @ClassName SysRoleReq
 * @Author mdframework
 * @Date 2021-02-02
 * @Version 1.0.0
 * @Description 列表查询信息体
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class SysRoleReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    
    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<SysRole> lqw) {
        lqw.eq("o.del_flag",0);
        if(StringUtils.isNotBlank(this.getRoleName())){
            lqw.eq("o.role_name",this.getRoleName());
        }
        if(StringUtils.isNotBlank(this.getStatus())){
            lqw.eq("o.status",this.getStatus());
        }
        if(StringUtils.isNotBlank(this.getRoleKey())){
            lqw.eq("o.role_key",this.getRoleKey());
        }
        if (StringUtils.isNotBlank(this.getBeginTime())){
            lqw.ge("o.create_time", this.getBeginTime());
        }
        if (StringUtils.isNotBlank(this.getEndTime())){
            lqw.le("o.create_time", this.getEndTime());
        }
        lqw.orderByAsc("o.role_sort");
    }

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色状态")
    private String status;

    @ApiModelProperty(value = "角色权限")
    private String roleKey;

    @ApiModelProperty(value = "开始检索时间")
    private String beginTime;

    @ApiModelProperty(value = "结束检索时间")
    private String endTime;

}
