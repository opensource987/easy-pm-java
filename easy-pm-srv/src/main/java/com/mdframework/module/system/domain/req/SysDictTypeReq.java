package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.system.domain.entity.SysDictType;
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
 * @ClassName SysDictTypeReq
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
public class SysDictTypeReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    
    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<SysDictType> lqw) {
        String alias = "o.";
        if (StringUtils.isNotBlank(this.getStatus())) {
            lqw.eq(alias+"STATUS",this.getStatus());
        }
        if (StringUtils.isNotBlank(this.getDictName())) {
            lqw.eq(alias+"dict_name",this.getDictName());
        }
        if (StringUtils.isNotBlank(this.getDictType())) {
            lqw.eq(alias+"dict_type",this.getDictType());
        }
        if (StringUtils.isNotBlank(this.getBeginTime())) {
            lqw.ge(alias+"create_time",this.getBeginTime());
        }
        if (StringUtils.isNotBlank(this.getEndTime())) {
            lqw.le(alias+"create_time",this.getEndTime());
        }

    }

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "状态(0正常1停用")
    private String status;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "开始时间检索")
    private String beginTime;

    @ApiModelProperty(value = "结束时间检索")
    private String endTime;
}
