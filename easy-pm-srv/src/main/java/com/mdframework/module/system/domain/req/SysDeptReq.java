package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.system.domain.entity.SysDept;
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
 * @ClassName SysDeptReq
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
public class SysDeptReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    
    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<SysDept> lqw,boolean isVo) {
        String alias = "d.";
        if (isVo) {
            alias = "d.";
            lqw.eq(alias+"del_status",0);
        }
        if(StringUtils.isNotBlank(this.deptName)){
            lqw.eq(alias+"dept_name",this.getDeptName());
        }
        if(StringUtils.isNotBlank(this.getStatus())){
            lqw.eq(alias+"status",this.getStatus());
        }
        if(this.parentId != null){
            lqw.eq(alias+"parent_id",this.getParentId());
        }
    }

    @ApiModelProperty("父部门id")
    private Long parentId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("删除标志（0正常 1删除）")
    private String delFlag;
}
