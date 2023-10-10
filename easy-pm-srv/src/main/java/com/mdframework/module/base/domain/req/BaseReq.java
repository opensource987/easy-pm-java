package com.mdframework.module.base.domain.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName BaseReq
 * @Author Jesse(573110463 @ qq.com)
 * @Date 2020-11-04 15:43 星期三
 * @Version 1.0
 * @Description 请求基础类
 */
@Data
public class BaseReq {
    @ApiModelProperty(value = "每页条数 默认10",example="10")
    private int pageSize;

    @ApiModelProperty(value = "页码，默认1" ,example = "1")
    private int pageNum;

}
