package com.mdframework.module.project.mapper;

import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.domain.req.ProjectStatistics;
import com.mdframework.module.project.domain.vo.ProjectVo;
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
 * @ClassName Project
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目信息Mapper接口
 */
public interface ProjectMapper extends BaseMapper<Project> {

    List<ProjectVo> listVo(@Param("ew") QueryWrapper<Project> lqw);
    List<SysUser> listUsersByProject(@Param("projectId")Long projectId);
    List<ProjectVo> listVoByUser(@Param("ew") QueryWrapper<Project> lqw, @Param("userId")Long userId);

    ProjectVo getVo(@Param("id")Long id);

    int batchAddProjectUsers(@Param("projectId") Long projectId, @Param("list") List<SysUser> list);
    int deleteProjectUsers(@Param("projectId") Long projectId);
    int checkProjectNameUnique(@Param("projectName") String projectName);
    ProjectStatistics projectStatistics();
}
