package com.mdframework.module.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.page.PageMethod;
import com.mdframework.common.annotation.DataScope;
import com.mdframework.common.constant.ProjectConstants;
import com.mdframework.module.project.domain.ProjectModuleExcel;
import com.mdframework.module.project.domain.ProjectModuleNode;
import com.mdframework.module.project.domain.req.ProjectReq;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.req.ProjectStatistics;
import com.mdframework.module.project.service.IProjectModuleService;
import com.mdframework.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.module.project.mapper.ProjectMapper;
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.domain.vo.ProjectVo;
import com.mdframework.module.project.service.IProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
 * @Description 项目信息Service业务层处理
 */
@Service
//@CacheConfig(cacheNames = {"project"})
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    private IProjectModuleService iProjectModuleService;
    /**
     * @Description 查询列表返回VO 用于返回给前端的列表接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [lqw]
     * @Return List<ProjectVo>
     */
    @Override
    @DataScope(userAlias = "pu")
//    @Cacheable(key = "targetClass + methodName + #p0.getSqlSelect()")
    public List<ProjectVo> listVo(QueryWrapper<Project> lqw, ProjectReq projectReq) {
        //指定分页到最后一个查询语句
        Page page = PageHelper.getLocalPage();
        PageMethod.clearPage();

        QueryWrapper<ProjectModule> pm_lqw = new QueryWrapper<>();
        if(projectReq.getUserIds() != null && projectReq.getUserIds().size()>0){
            //取出与需查询用户相关联项目模块关联的项目id
            pm_lqw.in("pu.user_id", projectReq.getUserIds());
            pm_lqw.eq("o.del_status", 0);
            List<Long> projectIds = new ArrayList<>();
            List<ProjectModuleVo> projectModules = iProjectModuleService.listVoByUser(pm_lqw);
            for(ProjectModuleVo projectModule : projectModules){
                if(!projectIds.contains(projectModule.getProjectId())){
                    projectIds.add(projectModule.getProjectId());
                }
            }
            if(projectIds.size()>0){
                lqw.and(projectQueryWrapper ->
                        projectQueryWrapper.in("pu.user_id", projectReq.getUserIds())
                        .or().in("o.id", projectIds)
                );
            }else {
                lqw.in("pu.user_id", projectReq.getUserIds());
            }
        }

        //指定分页到本查询语句
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<ProjectVo> projectVos = this.baseMapper.listVo(lqw);
        projectVos.forEach(projectVo -> {
            if(projectVo.getId() != null){
                projectVo.setUsers(this.listUsersByProject(projectVo.getId()));
            }
            if(SecurityUtils.getIsTestUser()){
                projectVo.setProjectName("Test");
            }
        });
        return projectVos;
    }

    @Override
//    @Cacheable(key = "targetClass + methodName + #p0.getSqlSelect()")
    public List<ProjectVo> listVoBy(QueryWrapper<Project> lqw) {
        List<ProjectVo> projectVos = this.baseMapper.listVo(lqw);
        return projectVos;
    }

    @Override
    public List<ProjectVo> listVoByUser(QueryWrapper<Project> lqw, Long userId) {
        List<ProjectVo> projectVos = this.baseMapper.listVoByUser(lqw, userId);
        if(SecurityUtils.getIsTestUser()){
            projectVos.forEach(projectVo -> {
                projectVo.setProjectName("Test");
            });
        }
        return projectVos;
    }

    @Override
//    @Cacheable(key = "targetClass + methodName + #p0")
    public List<SysUser> listUsersByProject(Long projectId) {
        List<SysUser> users = this.baseMapper.listUsersByProject(projectId);
        if(SecurityUtils.getIsTestUser()){
            users.forEach(user -> {
                user.setUserName("Test");
                user.setNickName("Test");
            });
        }
        return users;
    }

    /**
     * @Description 通过查询详情VO 用于返回给前端的列详情接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [id]
     * @Return ProjectVo
     */
    @Override
    public ProjectVo getVoById(Long id) {
        ProjectVo projectVo = (ProjectVo)this.baseMapper.getVo(id);
        if(SecurityUtils.getIsTestUser()){
            projectVo.setProjectName("Test");
        }
        return projectVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateProjectUsers(Long projectId, List<SysUser> users) {
        int result;
        //先清空用户再统一添加最新
        result = this.baseMapper.deleteProjectUsers(projectId);
        result = this.baseMapper.batchAddProjectUsers(projectId, users);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeProject(Long[] ids){
        //先删除项目
        this.removeByIds(Arrays.asList(ids));

        for(Long id : ids){
            //再删除模块
            UpdateWrapper<ProjectModule> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("del_status",1);
            updateWrapper.eq("project_id",id);
            iProjectModuleService.update(null,updateWrapper);
        }
        return 1;
    }

    @Override
    public String checkProjectNameUnique(String projectName)
    {
        int count = this.baseMapper.checkProjectNameUnique(projectName);
        if (count > 0)
        {
            return ProjectConstants.NOT_UNIQUE;
        }
        return ProjectConstants.UNIQUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int importProject(ProjectModuleExcel projectModuleExcel) {
        //创建项目
        Project project = new Project();
        project.setProjectName(projectModuleExcel.getProjectName());
        project.setProjectType(0);
        project.setProgress("0");
        project.setStatus(0);
        this.baseMapper.insert(project);
        Map parentModuleIdMap = new LinkedHashMap();
        System.out.println("projectId="+project.getId());
        for(ProjectModuleNode module : projectModuleExcel.getModuleList()){
            ProjectModule projectModule = new ProjectModule();
            projectModule.setModuleName(module.getModuleName());
            projectModule.setProjectId(project.getId());
            projectModule.setProgress("0");
            projectModule.setStatus(0);
            if(!StringUtils.isEmpty(module.getParentModuleName())){
                Long parentModuleId = (Long)parentModuleIdMap.get(module.getParentModuleName());
                projectModule.setParentId(parentModuleId);
            }else{
                projectModule.setParentId(0L);
            }
            iProjectModuleService.getBaseMapper().insert(projectModule);
            parentModuleIdMap.put(projectModule.getModuleName(), projectModule.getId());
        }

        return 0;
    }

    @Override
    public ProjectStatistics projectStatistics(){
        return this.baseMapper.projectStatistics();
    }

}
