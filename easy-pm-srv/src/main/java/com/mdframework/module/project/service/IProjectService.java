package com.mdframework.module.project.service;

import com.mdframework.module.project.domain.ProjectModuleExcel;
import com.mdframework.module.project.domain.req.ProjectReq;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.domain.req.ProjectStatistics;
import com.mdframework.module.project.domain.vo.ProjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * @Description 项目信息Service接口
 */
public interface IProjectService extends IService<Project> {

    /**
     * @Description 查询列表返回VO 用于返回给前端的列表接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [lqw]
     * @Return List<ProjectVo>
     */
    List<ProjectVo> listVo(QueryWrapper<Project> lqw, ProjectReq projectReq);

    List<ProjectVo> listVoBy(QueryWrapper<Project> lqw);

    /**
     * @Description 根据用户查询列表返回VO 用于返回给前端的列表接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [lqw]
     * @Return List<ProjectVo>
     */
    List<ProjectVo> listVoByUser(QueryWrapper<Project> lqw, Long userId);

    List<SysUser> listUsersByProject(Long projectId);

    /**
     * @Description 通过查询详情VO 用于返回给前端的列详情接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [id]
     * @Return ProjectVo
     */
    ProjectVo getVoById(Long id);

    int updateProjectUsers(Long projectId, List<SysUser> users);

    int removeProject(Long[] ids);
    String checkProjectNameUnique(String projectName);

    int importProject(ProjectModuleExcel projectModuleExcel);

    ProjectStatistics projectStatistics();

}
