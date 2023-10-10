package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.annotation.DataScope;
import com.mdframework.common.config.DingTalkProperties;
import com.mdframework.common.constant.UserConstants;
import com.mdframework.module.system.domain.entity.SysRole;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.common.exception.CustomException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.project.domain.Project;
import com.mdframework.module.project.domain.ProjectModule;
import com.mdframework.module.project.domain.vo.ProjectModuleVo;
import com.mdframework.module.project.domain.vo.ProjectVo;
import com.mdframework.module.project.service.IProjectModuleService;
import com.mdframework.module.project.service.IProjectService;
import com.mdframework.module.system.service.ISysConfigService;
import com.mdframework.module.system.service.ISysDeptService;
import com.mdframework.module.system.service.ISysUserService;
import com.mdframework.module.system.domain.SysPost;
import com.mdframework.module.system.domain.SysUserPost;
import com.mdframework.module.system.domain.SysUserRole;
import com.mdframework.module.system.mapper.*;
import com.mdframework.utils.SecurityUtils;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户 业务层处理
 *
 * @author mdframework
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements ISysUserService
{
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IProjectModuleService projectModuleService;





    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserList(SysUser user)
    {
        List<SysUser> users = userMapper.selectUserList(user);
        if(SecurityUtils.getIsTestUser()){
            users.forEach(userDetail ->{
                userDetail.setNickName("Test");
                userDetail.setUserName("Test");
                userDetail.setPhonenumber("123");
                userDetail.setEmail("123");
            });
        }
        return users;
    }

    /**
     * 根据条件分页查询用户关联项目列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
//    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUser> selectUserProjectList(SysUser user)
    {
        List<SysUser> users = userMapper.selectUserList(user);

        users.forEach(userDetail ->{
            if(SecurityUtils.getIsTestUser()){
                userDetail.setNickName("Test");
                userDetail.setUserName("Test");
            }
            //拿用户关联项目列表
            QueryWrapper<Project> project_lqw = new QueryWrapper<Project>();
            project_lqw.eq("o.del_status", 0);

            //只需拿正常，运维，售前项目
            List<Long> projectStatusList = new ArrayList<>();
            projectStatusList.add(0L);
            projectStatusList.add(4L);
            projectStatusList.add(5L);
            project_lqw.in("o.status", projectStatusList);

            project_lqw.ne("o.progress", 12);

            List<ProjectVo> projectVos = projectService.listVoByUser(project_lqw, userDetail.getUserId());
//            List<? extends Object> project_bases = projectVos;
            userDetail.setProjects(projectVos);

            //非项目组长则需要拿模块信息
            if(userDetail.getIsLeader() == null || !userDetail.getIsLeader().equals("1")){
                //拿用户关联项目模块列表
                QueryWrapper<ProjectModule> module_lqw = new QueryWrapper<ProjectModule>();
                module_lqw.eq("o.del_status", 0);

                //只需拿正常，运维，售前项目
                List<Long> moduleStatusList = new ArrayList<>();
                moduleStatusList.add(0L);
                moduleStatusList.add(4L);
                moduleStatusList.add(5L);
                module_lqw.in("o.status", moduleStatusList);
                module_lqw.ne("o.progress", 12);
                //排除完成开发项目
                module_lqw.in("p.status", projectStatusList);
                module_lqw.ne("p.progress", 12);

                module_lqw.in("pu.user_id",userDetail.getUserId());

                List<ProjectModuleVo> projectModuleVos = projectModuleService.listVoByUser(module_lqw);
                if(SecurityUtils.getIsTestUser()){
                    projectModuleVos.forEach(projectModuleVo -> {
                        projectModuleVo.setProjectName("Test");
                        projectModuleVo.setModuleName("Test");
                    });
                }
//                List<? extends Object> project_module_bases = projectModuleVos;
                userDetail.setProjectModules(projectModuleVos);
            }

        });
        return users;
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserByUserName(String userName)
    {
        return userMapper.selectUserByUserName(userName);
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUser selectUserById(Long userId)
    {
        return userMapper.selectUserById(userId);
    }

    @Override
    public SysUser selectUserByDingtalkUserId(String dingtalkUserId){
        return userMapper.selectUserByDingtalkUserId(dingtalkUserId);
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName)
    {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysRole role : list)
        {
            idsStr.append(role.getRoleName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName)
    {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        StringBuffer idsStr = new StringBuffer();
        for (SysPost post : list)
        {
            idsStr.append(post.getPostName()).append(",");
        }
        if (StringUtils.isNotEmpty(idsStr.toString()))
        {
            return idsStr.substring(0, idsStr.length() - 1);
        }
        return idsStr.toString();
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(String userName)
    {
        int count = userMapper.checkUserNameUnique(userName);
        if (count > 0)
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        if(user.getPhonenumber()==null || user.getPhonenumber().equals("")){
            return UserConstants.UNIQUE;
        }
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        if(user.getEmail()==null || user.getEmail().equals("")){
            return UserConstants.UNIQUE;
        }
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user)
    {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin())
        {
            throw new CustomException("不允许操作超级管理员用户");
        }
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user)
    {
        // 新增用户信息
        // Mybatisplus的insert插入失败，用代码实现
        user.setCreateTime(new Date());
        int rows = baseMapper.insert(user);
        //int rows = userMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SysUser user)
    {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);

        return updateQueryUser(user);
        //return userMapper.updateUser(user);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserStatus(SysUser user)
    {
        return updateQueryUser(user);
        //return userMapper.updateUser(user);
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserProfile(SysUser user)
    {
        return updateQueryUser(user);
        //return userMapper.updateUser(user);
    }

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar 头像地址
     * @return 结果
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar)
    {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetPwd(SysUser user)
    {
        return updateQueryUser(user);
        //return userMapper.updateUser(user);
    }

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    @Override
    public int resetUserPwd(String userName, String password)
    {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Long roleId : roles)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SysUser user)
    {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts))
        {
            // 新增用户与岗位管理
            List<SysUserPost> list = new ArrayList<SysUserPost>();
            for (Long postId : posts)
            {
                SysUserPost up = new SysUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long userId)
    {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return userMapper.deleteUserById(userId);
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] userIds)
    {
        for (Long userId : userIds)
        {
            checkUserAllowed(new SysUser(userId));
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new CustomException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SysUser user : userList)
        {
            try
            {
                // 验证是否存在这个用户
                SysUser u = userMapper.selectUserByUserName(user.getUserName());
                if (StringUtils.isNull(u))
                {
                    user.setPassword(SecurityUtils.encryptPassword(password));
                    user.setCreateBy(operName);
                    baseMapper.insertUser(user);
                    //this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    user.setUpdateBy(operName);
                    this.updateQueryUser(user);
                    //this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getUserName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new CustomException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    public int updateQueryUser(SysUser user){
        UpdateWrapper<SysUser> sysUserUpdateWrapper = new UpdateWrapper<>();
        sysUserUpdateWrapper.set(user.getDeptId()!=null&&user.getDeptId()!=0,"dept_id",user.getDeptId())
                .set("is_leader",user.getIsLeader())
            .set(StringUtils.isNotBlank(user.getUserName()),"user_name",user.getUserName())
            .set(StringUtils.isNotBlank(user.getNickName()),"nick_name",user.getNickName())
            .set(StringUtils.isNotBlank(user.getEmail()),"email",user.getEmail())
            .set(StringUtils.isNotBlank(user.getPhonenumber()),"phonenumber",user.getPhonenumber())
            .set(StringUtils.isNotBlank(user.getSex()),"sex",user.getSex())
            .set(StringUtils.isNotBlank(user.getAvatar()),"avatar",user.getAvatar())
            .set(StringUtils.isNotBlank(user.getPassword()),"password",user.getPassword())
            .set(StringUtils.isNotBlank(user.getStatus()),"status",user.getStatus())
            .set(StringUtils.isNotBlank(user.getLoginIp()),"login_ip",user.getLoginIp())
            .set(user.getLoginDate()!= null,"login_date",user.getLoginDate())
            .set(StringUtils.isNotBlank(user.getUpdateBy()),"update_by",user.getUpdateBy())
            .set(StringUtils.isNotBlank(user.getRemark()),"remark",user.getRemark())
            .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .eq("user_id",user.getUserId());
        return baseMapper.update(null,sysUserUpdateWrapper);
    }



    @Override
    public int batchDeleteByIds(List<Long> idList) {
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("del_flag", 2)
                .in("user_id",idList);
        int result = baseMapper.update(null,updateWrapper);
        return result;
    }



}
