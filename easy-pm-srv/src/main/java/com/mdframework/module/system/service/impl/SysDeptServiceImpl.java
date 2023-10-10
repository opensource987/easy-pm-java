package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.annotation.DataScope;
import com.mdframework.common.constant.UserConstants;
import com.mdframework.module.system.domain.TreeSelect;
import com.mdframework.module.system.domain.entity.SysDept;
import com.mdframework.module.system.domain.entity.SysRole;
import com.mdframework.common.exception.CustomException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysDeptService;
import com.mdframework.module.system.mapper.SysDeptMapper;
import com.mdframework.module.system.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 * 
 * @author mdframework
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper,SysDept> implements ISysDeptService
{
    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 查询部门管理数据
     * 
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDept> selectDeptList(SysDept dept)
    {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 构建前端所需要树结构
     * 
     * @param depts 部门列表
     * @return 树结构列表
     */
    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts)
    {
        List<SysDept> returnList = new ArrayList<SysDept>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysDept dept : depts)
        {
            tempList.add(dept.getDeptId());
        }
        for (Iterator<SysDept> iterator = depts.iterator(); iterator.hasNext();)
        {
            SysDept dept = (SysDept) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId()))
            {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = depts;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts)
    {
        Collections.reverse(depts);
        List<SysDept> deptTrees = buildDeptTree(depts);
        return deptTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据角色ID查询部门树信息
     * 
     * @param roleId 角色ID
     * @return 选中部门列表
     */
    @Override
    public List<Integer> selectDeptListByRoleId(Long roleId)
    {
        SysRole role = roleMapper.selectRoleById(roleId);
        return deptMapper.selectDeptListByRoleId(roleId, role.isDeptCheckStrictly());
    }

    /**
     * 根据部门ID查询信息
     * 
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId)
    {
        return deptMapper.selectDeptById(deptId);
    }

    /**
     * 根据ID查询所有子部门（正常状态）
     * 
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId)
    {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 是否存在子节点
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public boolean hasChildByDeptId(Long deptId)
    {
        int result = deptMapper.hasChildByDeptId(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 查询部门是否存在用户
     * 
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId)
    {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 校验部门名称是否唯一
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDept dept)
    {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDept info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (info!=null){
            if (info!=null && !UserConstants.DEPT_NORMAL.equals(info.getStatus()))
            {
                throw new CustomException("部门停用，不允许新增");
            }
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        }

        //Mybatisplus的insert插入时间失败，用代码实现
        dept.setCreateTime(new Date());
        return baseMapper.insert(dept);
        //return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     * 
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int updateDept(SysDept dept)
    {
        SysDept newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDept oldDept = deptMapper.selectDeptById(dept.getDeptId());
        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept))
        {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(dept.getParentId()!=null&&dept.getParentId()!=0,"parent_id",dept.getParentId())
            .set(StringUtils.isNotBlank(dept.getDeptName()),"dept_name",dept.getDeptName())
            .set(StringUtils.isNotBlank(dept.getAncestors()),"ancestors",dept.getAncestors())
            .set(dept.getOrderNum()!=null&&dept.getOrderNum()!=0,"order_num",dept.getOrderNum())
            .set(StringUtils.isNotBlank(dept.getLeader()),"leader",dept.getLeader())
            .set(StringUtils.isNotBlank(dept.getPhone()),"phone",dept.getPhone())
            .set(StringUtils.isNotBlank(dept.getEmail()),"email",dept.getEmail())
            .set(StringUtils.isNotBlank(dept.getStatus()),"status",dept.getStatus())
            .set(StringUtils.isNotBlank(dept.getUpdateBy()),"update_by",dept.getUpdateBy())
            .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .set(dept.getWxcpDeptId()!=null&&dept.getWxcpDeptId()!=0,"wxcp_dept_id",dept.getWxcpDeptId())
            .set(dept.getWxcpParentId()!=null&&dept.getWxcpParentId()!=0,"wxcp_parent_id",dept.getWxcpParentId())
            .set(StringUtils.isNotBlank(dept.getPlatform()),"platform",dept.getPlatform())
            .eq("dept_id",dept.getDeptId());
        int result = baseMapper.update(null,updateWrapper);
        //int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()))
        {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatus(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     * 
     * @param dept 当前部门
     */
    private void updateParentDeptStatus(SysDept dept)
    {
        String updateBy = dept.getUpdateBy();
        dept = deptMapper.selectDeptById(dept.getDeptId());
        dept.setUpdateBy(updateBy);
        deptMapper.updateDeptStatus(dept);
    }

    /**
     * 修改子元素关系
     * 
     * @param deptId 被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors)
    {
        List<SysDept> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDept child : children)
        {
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
        }
        if (children.size() > 0)
        {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 删除部门管理信息
     * 
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId)
    {
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public int insertOrUpdateWxcpDept(SysDept dept) {
        //处理父部门ParentId参数
        if(dept.getWxcpParentId()!=null && dept.getWxcpParentId()!=0){
            //关联父部门id
            SysDept sysParentDept = selectDeptByWxcpDetpId(dept.getWxcpParentId());
            if(sysParentDept!=null){
                dept.setParentId(sysParentDept.getDeptId());
            }
        }else{
            //getWxcpParentId()==0或者不存在，则把该部门设为交部门
            dept.setParentId(dept.getWxcpParentId());
            dept.setAncestors(String.valueOf(dept.getWxcpParentId()));
        }

        int result;
        //查询是否已存在该部门，存在则更新，不存在则插入
        QueryWrapper<SysDept> deptQueryWrapper=new QueryWrapper();
        deptQueryWrapper.eq("wxcp_dept_id",dept.getWxcpDeptId())
                .eq("del_flag", 0);
        SysDept sysDept = deptMapper.selectOne(deptQueryWrapper);
        if(sysDept != null){
            sysDept.setDeptName(dept.getDeptName());
            sysDept.setOrderNum(dept.getOrderNum());
            sysDept.setParentId(dept.getParentId());
            sysDept.setAncestors(dept.getAncestors());
            result = this.updateDept(sysDept);
        }else{
            result = this.insertDept(dept);
        }
        return result;
    }

    @Override
    public List<SysDept> listDeptByPlatform(String platform) {
        QueryWrapper<SysDept> deptQueryWrapper=new QueryWrapper();
        deptQueryWrapper.eq("platform",platform)
                .eq("del_flag", 0);
        List<SysDept> sysDepts = deptMapper.selectList(deptQueryWrapper);
        return sysDepts;
    }

    @Override
    public int batchDeleteByIds(List<Long> idList) {
        UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("del_flag", 2)
                .in("dept_id",idList);
        int result = baseMapper.update(null,updateWrapper);
        return result;
    }

    @Override
    public SysDept selectDeptByWxcpDetpId(Long wxcpDetpId) {
        QueryWrapper<SysDept> queryWrapper=new QueryWrapper();
        queryWrapper.eq("wxcp_dept_id",wxcpDetpId)
                .eq("del_flag", 0);
        return deptMapper.selectOne(queryWrapper);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t)
    {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysDept tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t)
    {
        List<SysDept> tlist = new ArrayList<SysDept>();
        Iterator<SysDept> it = list.iterator();
        while (it.hasNext())
        {
            SysDept n = (SysDept) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getDeptId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysDept> list, SysDept t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
