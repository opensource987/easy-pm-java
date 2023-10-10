package com.mdframework.module.project.mapper;

import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectModule
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目模块Mapper接口
 */
public interface ProjectModuleMapper extends BaseMapper<ProjectModule> {

    List<ProjectModuleVo> listVo(@Param("ew") QueryWrapper<ProjectModule> lqw);
    List<ProjectModuleVo> listVoByUser(@Param("ew") QueryWrapper<ProjectModule> lqw);

    ProjectModuleVo getVo(@Param("id")Long id);

    int batchAddModuleUsers(@Param("moduleId") Long moduleId, @Param("list") List<SysUser> list);
    int deleteModuleUsers(@Param("moduleId") Long moduleId);
}
