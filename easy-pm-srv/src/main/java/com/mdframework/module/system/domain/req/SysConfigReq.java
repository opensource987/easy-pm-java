package com.mdframework.module.system.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.annotation.Excel;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.system.domain.SysConfig;
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
 * @ClassName SysConfigReq
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
public class SysConfigReq extends BaseReq {
    private static final long serialVersionUID = 1L;


    /**
     * @Description 生成查询wrapper
     * @Author Jesse(573110463 @ qq.com)
     * @Date 2020-11-04 16:04
     * @Param [lqw]
     * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
     * @Return void
     */
    public void generatorQuery(QueryWrapper<SysConfig> lqw) {
        String alias = "o.";
        if (StringUtils.isNotBlank(this.getConfigName())) {
            lqw.like(alias + "config_name", this.getConfigName());
        }
        if (StringUtils.isNotBlank(this.getConfigType())) {
            lqw.eq(alias + "config_type", this.getConfigType());
        }
        if (StringUtils.isNotBlank(this.getConfigKey())) {
            lqw.eq(alias + "config_key", this.getConfigKey());
        }
        if (StringUtils.isNotBlank(this.getBeginTime())) {
            lqw.ge(alias + "create_time", this.getBeginTime());
        }
        if (StringUtils.isNotBlank(this.getEndTime())) {
            lqw.le(alias + "create_time", this.getEndTime());
        }

    }

    @ApiModelProperty("参数名称")
    private String configName;

    @ApiModelProperty("参数键名")
    private String configKey;

    @ApiModelProperty("参数键值")
    private String configValue;

    @ApiModelProperty("系统内置（Y是 N否）")
    private String configType;

    @ApiModelProperty(value = "开始检索时间")
    private String beginTime;

    @ApiModelProperty(value = "结束检索时间")
    private String endTime;

}
