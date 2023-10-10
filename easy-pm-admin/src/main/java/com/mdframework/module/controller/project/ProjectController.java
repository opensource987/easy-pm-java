package com.mdframework.module.controller.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.*;
import java.util.*;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.mdframework.common.constant.ProjectConstants;
import com.mdframework.module.project.domain.ProjectModuleExcel;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.project.domain.req.ProjectStatistics;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.domain.vo.ProjectVo;
import com.mdframework.module.project.domain.req.ProjectReq;
import com.mdframework.module.project.domain.req.ProjectAdd;
import com.mdframework.module.project.domain.req.ProjectUpdate;
import com.mdframework.module.project.service.IProjectService;
import com.mdframework.utils.poi.ExcelUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import io.swagger.annotations.*;
import com.mdframework.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**

 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectController
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目信息Controller
 */
@Validated
@Api(tags = "项目信息")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/project/project" )
public class ProjectController extends BaseController {

    private final IProjectService iProjectService;

 /**
  * @Description 分页
  * @Author mdframework
  * @Date 2021-09-14
  * @Param project
  * @Return TableDataInfo
 */
    @ApiOperation("列表")
    @PreAuthorize("@ss.hasPermi('project:project:list')")
    @GetMapping("/list")
    public TableDataInfo<ProjectVo> list(ProjectReq projectReq)
    {
        startPage();
        QueryWrapper<Project> lqw = new QueryWrapper<Project>();
        projectReq.generatorQuery(lqw,true);
        logger.info("projectReq=", projectReq);
        List<ProjectVo> list = iProjectService.listVo(lqw, projectReq);
        return getDataTable(list);
    }

    /**
     * @Description 新增
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermi('project:project:add')" )
    @Log(title = "添加项目信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody ProjectAdd projectAdd) {
        Project project = Convert.convert(new TypeReference<Project>() {}, projectAdd);
        String projectName = project.getProjectName();
        if (!StringUtils.isEmpty(projectName) && ProjectConstants.NOT_UNIQUE.equals(iProjectService.checkProjectNameUnique(projectName)))
        {
            return AjaxResult.error("新增项目：”" + projectName + "失败，项目名已存在");
        }

        project.setOrderNum(System.currentTimeMillis());
        return toAjax(iProjectService.save(project) ? 1 : 0);
    }

    /**
     * @Description 修改
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermi('project:project:edit')" )
    @Log(title = "修改项目信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody ProjectUpdate projectUpdate) {
        Project project = Convert.convert(new TypeReference<Project>() {}, projectUpdate);
        String projectName = project.getProjectName();
        if (!StringUtils.isEmpty(projectName) && ProjectConstants.NOT_UNIQUE.equals(iProjectService.checkProjectNameUnique(projectName)))
        {
            return AjaxResult.error("修改项目：”" + projectName + "”失败，项目名已存在");
        }
        if(project.getStatus() == 2){
            Project oldProjectData = iProjectService.getById(project.getId());
            if(oldProjectData.getStatus() != 2){
                //说明项目状态从其他状态改为已完结状态，需更新完结时间
                project.setCloseTime(new Date());
            }
        }

        return toAjax(iProjectService.updateById(project) ? 1 : 0);
    }

    /**
     * @Description 修改项目关联用户
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("修改项目关联用户")
    @PreAuthorize("@ss.hasPermi('project:project:editUsers')" )
    @Log(title = "修改项目关联用户" , businessType = BusinessType.UPDATE)
    @PostMapping("/editProjectUsers")
    public AjaxResult editProjectUsers(@Valid @RequestBody ProjectUpdate projectUpdate) {
        logger.info("projectUpdate={}",projectUpdate);
        return toAjax(iProjectService.updateProjectUsers(projectUpdate.getId(), projectUpdate.getUsers()));
    }


    /**
     * @Description 导出
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("导出")
    @PreAuthorize("@ss.hasPermi('project:project:export')" )
    @Log(title = "导出项目信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(ProjectReq projectReq) {
        QueryWrapper<Project> lqw = new QueryWrapper<Project>();
        projectReq.generatorQuery(lqw,true);
        List<ProjectVo> list = iProjectService.listVo(lqw, projectReq);
        ExcelUtil<ProjectVo> util = new ExcelUtil<ProjectVo>(ProjectVo. class);
        return util.exportExcel(list, "project" );
    }

    /**
     * @Description 详情
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('project:project:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult<ProjectVo> getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iProjectService.getVoById(id));
    }



    /**
     * @Description 删除
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermi('project:project:remove')" )
    @Log(title = "删除项目信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iProjectService.removeProject(ids));
    }

    public static ResponseEntity<byte[]> buildResponseEntity(File file) throws IOException {
        byte[] body = null;
        //获取文件
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        is.read(body);
        HttpHeaders headers = new HttpHeaders();
        //设置文件类型
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        //设置Http状态码
        HttpStatus statusCode = HttpStatus.OK;
        //返回数据
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(body, headers, statusCode);
        return entity;
    }

    /**
     * @Description 项目状态统计
     * @Author mdframework
     * @Date 2021-09-14
     * @Param project
     * @Return TableDataInfo
     */
    @ApiOperation("项目状态统计")
    @PreAuthorize("@ss.hasPermi('project:project:statistics')" )
    @GetMapping("/statistics")
    public AjaxResult<ProjectStatistics> statistics() {
        return AjaxResult.success(iProjectService.projectStatistics());
    }
}
