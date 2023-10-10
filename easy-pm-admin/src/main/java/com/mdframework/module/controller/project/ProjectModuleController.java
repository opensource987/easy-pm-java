package com.mdframework.module.controller.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mdframework.common.annotation.Log;
import com.mdframework.common.core.controller.BaseController;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.enums.BusinessType;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.mdframework.module.project.domain.req.ProjectModuleReq;
import com.mdframework.module.project.domain.req.ProjectModuleAdd;
import com.mdframework.module.project.domain.req.ProjectModuleUpdate;
import com.mdframework.module.project.service.IProjectModuleService;
import com.mdframework.utils.poi.ExcelUtil;
import javax.validation.Valid;
import io.swagger.annotations.*;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectModuleController
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目模块Controller
 */
@Validated
@Api(tags = "项目模块")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/project/module" )
public class ProjectModuleController extends BaseController {

    private final IProjectModuleService iProjectModuleService;

 /**
  * @Description 分页
  * @Author mdframework
  * @Date 2021-09-14
  * @Param projectModule
  * @Return TableDataInfo
 */
    @ApiOperation("列表")
    @PreAuthorize("@ss.hasPermi('project:module:list')")
    @GetMapping("/list")
    public AjaxResult list(ProjectModuleReq projectModuleReq)
    {
        List<ProjectModuleVo> list = iProjectModuleService.listVo(projectModuleReq);
        return AjaxResult.success(list);
    }

    /**
     * @Description 新增
     * @Author mdframework
     * @Date 2021-09-14
     * @Param projectModule
     * @Return TableDataInfo
     */
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermi('project:module:add')" )
    @Log(title = "添加项目模块" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ProjectModuleAdd projectModuleAdd) {
        ProjectModule projectModule = Convert.convert(new TypeReference<ProjectModule>() {}, projectModuleAdd);
        projectModule.setOrderNum(System.currentTimeMillis());
        AjaxResult result = toAjax(iProjectModuleService.save(projectModule) ? 1 : 0);
        iProjectModuleService.clearCache(projectModuleAdd.getProjectId());
        return result;
    }

    /**
     * @Description 修改
     * @Author mdframework
     * @Date 2021-09-14
     * @Param projectModule
     * @Return TableDataInfo
     */
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermi('project:module:edit')" )
    @Log(title = "修改项目模块" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ProjectModuleUpdate projectModuleUpdate) {
        ProjectModule projectModule = Convert.convert(new TypeReference<ProjectModule>() {}, projectModuleUpdate);
        AjaxResult result = toAjax(iProjectModuleService.updateAndCalcModule(projectModule));
        return result;
    }

    /**
     * @Description 修改项目关联用户
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("修改模块关联用户")
    @PreAuthorize("@ss.hasPermi('project:module:editUsers')" )
    @Log(title = "修改模块关联用户" , businessType = BusinessType.UPDATE)
    @PostMapping("/editModuleUsers")
    public AjaxResult editModuleUsers(@Valid @RequestBody ProjectModuleUpdate projectModuleUpdate) {
        logger.info("projectModuleUpdate={}",projectModuleUpdate);
        AjaxResult result = toAjax(iProjectModuleService.updateModuleUsers(projectModuleUpdate.getId(), projectModuleUpdate.getUsers()));
        return result;
    }


    /**
     * @Description 导出
     * @Author mdframework
     * @Date 2021-09-14
     * @Param projectModule
     * @Return TableDataInfo
     */
    @ApiOperation("导出")
    @PreAuthorize("@ss.hasPermi('project:module:export')" )
    @Log(title = "导出项目模块" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(ProjectModuleReq projectModuleReq) {
        List<ProjectModuleVo> list = iProjectModuleService.listVo(projectModuleReq);
        ExcelUtil<ProjectModuleVo> util = new ExcelUtil<ProjectModuleVo>(ProjectModuleVo. class);
        return util.exportExcel(list, "module" );
    }

    /**
     * @Description 详情
     * @Author mdframework
     * @Date 2021-09-14
     * @Param projectModule
     * @Return TableDataInfo
     */
    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('project:module:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult<ProjectModuleVo> getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iProjectModuleService.getVoById(id));
    }



    /**
     * @Description 删除
     * @Author mdframework
     * @Date 2021-09-14
     * @Param projectModule
     * @Return TableDataInfo
     */
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermi('project:module:remove')" )
    @Log(title = "删除项目模块" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        List<Long> projectIds = new ArrayList<>();
        for(Long id: ids){
            ProjectModule projectModule = iProjectModuleService.getBaseMapper().selectById(id);
            projectIds.add(projectModule.getProjectId());
        }

        AjaxResult result = toAjax(iProjectModuleService.removeByIds(Arrays.asList(ids)) ? 1 : 0); //软删

        //清空缓存
        for(Long projectId : projectIds){
            iProjectModuleService.clearCache(projectId);
        }

        return result;
    }
}
