package com.mdframework.module.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.annotation.DataScope;
import com.mdframework.common.constant.UserConstants;
import com.mdframework.module.system.domain.entity.SysRole;
import com.mdframework.common.exception.CustomException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.common.utils.spring.SpringUtils;
import com.mdframework.module.system.service.ISysRoleService;
import com.mdframework.module.system.domain.SysRoleDept;
import com.mdframework.module.system.domain.SysRoleMenu;
import com.mdframework.module.system.domain.vo.SysRoleVo;
import com.mdframework.module.system.mapper.SysRoleDeptMapper;
import com.mdframework.module.system.mapper.SysRoleMapper;
import com.mdframework.module.system.mapper.SysRoleMenuMapper;
import com.mdframework.module.system.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 角色 业务层处理
 * 
 * @author mdframework
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    @Autowired
    private ISysRoleService iSysRoleService;

    @Override
    public List<SysRoleVo> listVo(QueryWrapper<SysRole> lqw) {
        return this.baseMapper.listVo(lqw);
    }

    /**
     * 根据条件分页查询角色数据
     * 
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     * 
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     * 
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     * 
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员角色");
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 新增保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // 新增角色信息
        //Mybatisplus的insert插入失败，用代码实现
        role.setCreateTime(new Date());
        this.baseMapper.insert(role);
        //roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        // 修改角色信息
        updateWrapperRole(role);
        //roleMapper.updateRole(role);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改角色状态
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(SysRole role)
    {
        return updateWrapperRole(role);
        //return roleMapper.updateRole(role);
    }

    /**
     * 修改数据权限信息
     * 
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // 修改角色信息
        updateWrapperRole(role);
        //roleMapper.updateRole(role);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜单信息
     * 
     * @param role 角色对象
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新增用户与角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // 新增角色与部门（数据权限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 通过角色ID删除角色
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleById(Long roleId)
    {
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     * 
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleByIds(Long[] roleIds)
    {
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        return roleMapper.deleteRoleByIds(roleIds);
    }

    public int updateWrapperRole(SysRole role){
        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtils.isNotBlank(role.getRoleName()),"role_name",role.getRoleName())
            .set(StringUtils.isNotBlank(role.getRoleKey()),"role_key",role.getRoleKey())
            .set(StringUtils.isNotBlank(role.getRoleSort()),"role_sort",role.getRoleSort())
            .set(StringUtils.isNotBlank(role.getDataScope()),"data_scope",role.getDataScope())
            .set(ObjectUtil.isNotNull(role.getMenuCheckStrictly()),"menu_check_strictly",role.getMenuCheckStrictly())
            .set(ObjectUtil.isNotNull(role.getDeptCheckStrictly()),"dept_check_strictly",role.getDeptCheckStrictly())
            .set(StringUtils.isNotBlank(role.getStatus()),"status",role.getStatus())
            .set(StringUtils.isNotBlank(role.getRemark()),"remark",role.getRemark())
            .set(StringUtils.isNotBlank(role.getUpdateBy()),"update_by",role.getUpdateBy())
            .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .eq("role_id",role.getRoleId());
        return baseMapper.update(null,updateWrapper);
    }
}
