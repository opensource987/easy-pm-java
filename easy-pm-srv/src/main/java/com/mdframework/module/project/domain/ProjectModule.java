package com.mdframework.module.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.mdframework.module.system.domain.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import java.util.List;

import com.mdframework.common.core.domain.BasePlusEntity;

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
 * @Description 项目模块 PO对象
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("epm_project_module")
public class ProjectModule extends BasePlusEntity {
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;
    /** 模块名 */
    private String moduleName;

    @TableField(exist = false)
    private String parentModuleName;
    /** 父级模块名 */
    private Long parentId;
    /** 模块进度 */
    private String progress;
    /** 关联项目id */
    private Long projectId;
    /** 关联项目名 */
    @TableField(exist = false)
    private String projectName;
    /** 模块启动时间 */
    private Date moduleBeginTime;
    /** 模块预计结束时间 */
    private Date modulePredictEndTime;

    //模块关联用户
    @TableField(exist = false)
    private List<SysUser> users;

    /** 0为正常，1为暂停，2为完成，3为取消 */
    private Integer status;
    private Long orderNum;
    /** 备注 */
    private String remark;
    /** 删除标志（0代表存在 2代表删除） */
//    private String delFlag;
}
