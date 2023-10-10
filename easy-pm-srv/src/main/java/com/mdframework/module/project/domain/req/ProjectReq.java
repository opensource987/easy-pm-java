package com.mdframework.module.project.domain.req;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mdframework.common.annotation.Excel;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.base.domain.req.BaseReq;
import com.mdframework.module.project.domain.Project;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectReq
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 列表查询信息体
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectReq extends BaseReq {
    private static final long serialVersionUID = 1L;

    private List<Long> statusIds;
    private List<Long> projectTypeIds;
    private List<Long> userIds;
    private String projectName;
    private String orderBy;
    private String sortType;
    /**
    * @Description 生成查询wrapper
    * @Author Jesse(573110463@qq.com)
    * @Date 2020-11-04 16:04
    * @Param [lqw]
    * @Param [isVO] 是否vo查询体，true的话走的是mapper.xml里面的查询语句，不过查询语法还是mybatis-plus，主要用于某些关联查询出一些非数据表字段
    * @Return void
    */
    public void generatorQuery(QueryWrapper<Project> lqw,boolean isVo) {
        String alias = "o.";
        if (isVo) {
//            alias = "o.";
            lqw.eq(alias+"del_status",0);
        }
        if(statusIds != null && statusIds.size()>0){
            lqw.in(alias+"status", statusIds);
        }
        if(projectTypeIds != null && projectTypeIds.size()>0){
            lqw.in(alias+"project_type", projectTypeIds);
        }
//        if(userIds != null && userIds.size()>0){
//            lqw.in("pu.user_id", userIds);
//        }
        if(projectName != null && !projectName.equals("")){
            lqw.like(alias+"project_name", projectName);
        }
        lqw.groupBy(alias+"id");
        //o.project_type asc, o.progress desc, o.id asc, o.order_num asc
        lqw.orderByAsc(alias+"project_type");
        if(!StringUtils.isEmpty(orderBy) && !StringUtils.isEmpty(sortType)){
            //驼峰转下划线
            orderBy = StringUtils.humpToLine(orderBy);
            if(sortType.equals("asc")){
                lqw.orderByAsc(alias+orderBy);
            }else if(sortType.equals("desc")){
                lqw.orderByDesc(alias+orderBy);
            }
        }
        lqw.orderByDesc(alias+"progress");
        lqw.orderByAsc(alias+"id");
        lqw.orderByAsc(alias+"order_num");
    }
}
