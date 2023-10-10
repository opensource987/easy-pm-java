package com.mdframework.module.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdframework.common.constant.Constants;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.module.project.domain.req.ProjectModuleReq;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.service.IProjectService;
import com.mdframework.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.module.project.mapper.ProjectModuleMapper;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.mdframework.module.project.service.IProjectModuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
 * @Description 项目模块Service业务层处理
 */
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class ProjectModuleServiceImpl extends ServiceImpl<ProjectModuleMapper, ProjectModule> implements IProjectModuleService {

    @Autowired
    private IProjectService iProjectService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * @Description 查询列表返回VO 用于返回给前端的列表接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [lqw]
     * @Return List<ProjectModuleVo>
     */
    @Override
    public List<ProjectModuleVo> listVo(ProjectModuleReq projectModuleReq) {
        //先查缓存有没有
        ValueOperations<String, String> redisOps = redisTemplate.opsForValue();
        String ProjectModulesKey = Constants.ProjectModulesKeyPrefix + ":" + projectModuleReq.getProjectId();
        if(!SecurityUtils.getIsTestUser()) {//非测试用户
            Object object = redisOps.get(ProjectModulesKey);
            if (object != null && !"[]".equals(object)) {
                return JSON.parseArray(object.toString(), ProjectModuleVo.class);
            }
        }

        QueryWrapper<ProjectModule> lqw = new QueryWrapper<ProjectModule>();
        projectModuleReq.generatorQuery(lqw,true);
        List<ProjectModuleVo> projectModuleVos = this.baseMapper.listVo(lqw);

        if(SecurityUtils.getIsTestUser()){
            projectModuleVos.forEach(projectModuleVo ->{
                projectModuleVo.setModuleName("Test");
                projectModuleVo.getUsers().forEach(user ->{
                    user.setUserName("Test");
                    user.setNickName("Test");
                });
            });
        }else {
            //保存到缓存
            redisOps.set(ProjectModulesKey, JSONObject.toJSONString(projectModuleVos), (int)(Math.random()*100+3500), TimeUnit.SECONDS); //随机失效时间，防止雪崩
        }
        return projectModuleVos;
    }

    @Override
    public List<ProjectModuleVo> listVoByUser(QueryWrapper<ProjectModule> lqw) {
        List<ProjectModuleVo> projectModuleVos = this.baseMapper.listVoByUser(lqw);
        if(SecurityUtils.getIsTestUser()){
            projectModuleVos.forEach(projectModuleVo ->{
                projectModuleVo.setModuleName("Test");
            });
        }
        return projectModuleVos;
    }



    /**
     * @Description 通过查询详情VO 用于返回给前端的列详情接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [id]
     * @Return ProjectModuleVo
     */
    @Override
    public ProjectModuleVo getVoById(Long id) {
        ProjectModuleVo projectModuleVo = this.baseMapper.getVo(id);
        if(SecurityUtils.getIsTestUser()){
            projectModuleVo.setModuleName("Test");
        }
        return projectModuleVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateModuleUsers(Long moduleId, List<SysUser> users) {
        int result;
        //先清空用户再统一添加最新
        result = this.baseMapper.deleteModuleUsers(moduleId);
        if(users != null && users.size()>0){
            result = this.baseMapper.batchAddModuleUsers(moduleId, users);
        }
        ProjectModuleVo projectModuleVo = this.baseMapper.getVo(moduleId);
        this.clearCache(projectModuleVo.getProjectId());
        return result;
    }

    @Override
    public int updateAndCalcModule(ProjectModule projectModule) {

        int result = this.updateById(projectModule) ? 1 : 0;
        //同时自动重新计算整个项目进度，并更新
        this.calcProjectProgress(projectModule);
        this.clearCache(projectModule.getProjectId());
        return result;
    }

    public int calcProjectProgress(ProjectModule projectModule){
        if(!StringUtils.isEmpty(projectModule.getProgress()) && projectModule.getProjectId()!= null){
            this.updateModuleProgress(projectModule);
        }
        return 0;
    }

    public void updateModuleProgress(ProjectModule projectModule){
        Long parentId = projectModule.getParentId();
        if(parentId == null){
            return;
        }
        QueryWrapper<ProjectModule> lqw = new QueryWrapper<>();
        lqw.eq("o.parent_id", projectModule.getParentId());
        lqw.eq("o.project_id", projectModule.getProjectId());
        lqw.eq("o.del_status", 0);
        List<ProjectModuleVo> projectModuleVos = this.baseMapper.listVo(lqw);
        if(projectModuleVos != null && projectModuleVos.size()>0){
            parentId = null;
            //模块总天数
            Double totalDays = 0.0;
            //每个模块sum(天数*进度)
            Double totalDaysMultiProgress = 0.0;
            for(ProjectModuleVo projectModuleVo : projectModuleVos){
                parentId = projectModuleVo.getParentId();
                if(projectModuleVo.getModulePredictEndTime() == null || projectModuleVo.getModuleBeginTime()==null){
                    //说明没有设置项目时间，停止更新进度
                    return;
                }
                Long duraDays = diffDays(projectModuleVo.getModulePredictEndTime(),projectModuleVo.getModuleBeginTime());
                totalDays += duraDays;
                totalDaysMultiProgress += projectModuleVo.getProgress() * duraDays;
            }
            Long avgProgress = Long.valueOf(Math.round(totalDaysMultiProgress/totalDays));
            if(parentId == 0L){
                //说明是根模块，更新项目进度
                Project project = iProjectService.getById(projectModule.getProjectId());
                if(StringUtils.isNotEmpty(project.getProgress()) && project.getProgress().equals("11")){
                    //如果项目原本进度为迭代，则不改动
                    return;
                }else if(avgProgress == 11){
                    //如果需要将项目进度更新为迭代，则不改动
                    return;
                }
                project.setProgress(avgProgress.toString());
                iProjectService.updateById(project);
            }else{
                //说明还不是根模块，先更新本模块上级模块
                ProjectModule projectModule1 = this.getById(parentId);
                projectModule1.setProgress(avgProgress.toString());
                this.updateById(projectModule1);
                //再进入递归继续更新上上级模块进度
                this.updateModuleProgress(projectModule1);
            }

        }
    }

    public Long diffDays(Date day1, Date day2){
        if(day1==null || day2==null){
            return 0L;
        }
        //这样得到的差值是毫秒级别
        Long diff = day1.getTime() - day2.getTime();
        Long days = diff / (1000 * 60 * 60 * 24);
        return days;
    }

    @Override
    public void clearCache(Long projectId){
        if(projectId == null){
            return;
        }
        //清除缓存
        final Set<String> keys = redisTemplate.keys(Constants.ProjectModulesKeyPrefix + ":" + projectId);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
