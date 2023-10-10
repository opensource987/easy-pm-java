package com.mdframework.module.project.service;

import com.mdframework.module.project.domain.req.ProjectModuleReq;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.baomidou.mybatisplus.extension.service.IService;
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
 * @Description 项目模块Service接口
 */
public interface IProjectModuleService extends IService<ProjectModule> {

    /**
     * @Description 查询列表返回VO 用于返回给前端的列表接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [lqw]
     * @Return List<ProjectModuleVo>
     */
    List<ProjectModuleVo> listVo(ProjectModuleReq projectModuleReq);
    List<ProjectModuleVo> listVoByUser(QueryWrapper<ProjectModule> lqw);

    /**
     * @Description 通过查询详情VO 用于返回给前端的列详情接口
     * @Author mdframework
     * @Date 2021-09-14
     * @Param [id]
     * @Return ProjectModuleVo
     */
    ProjectModuleVo getVoById(Long id);

    int updateModuleUsers(Long moduleId, List<SysUser> users);

    int updateAndCalcModule(ProjectModule projectModule);

//    int calcProjectProgress(ProjectModule projectModule);

    void clearCache(Long projectId);
}
