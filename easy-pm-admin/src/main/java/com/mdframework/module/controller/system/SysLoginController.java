package com.mdframework.module.controller.system;

import java.util.*;

import com.mdframework.common.config.WxCpConfiguration;
import com.mdframework.common.core.controller.BaseController;
import com.mdframework.module.system.service.ISysMenuService;
import com.mdframework.module.system.domain.LoginUser;
//import com.mdframework.rabbitmq.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.cp.bean.WxCpOauth2UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.mdframework.common.constant.Constants;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.module.system.domain.entity.SysMenu;
import com.mdframework.module.system.domain.entity.SysUser;
import com.mdframework.module.system.domain.model.LoginBody;
import com.mdframework.common.utils.ServletUtils;
import com.mdframework.module.system.service.SysLoginService;
import com.mdframework.module.system.service.SysPermissionService;
import com.mdframework.module.system.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录验证
 *
 * @author mdframework
 */
@Api(tags = "系统登录")
@RestController
public class SysLoginController extends BaseController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;




    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Map login(@RequestBody LoginBody loginBody)
    {
        Map<String,Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public Map getInfo()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String,Object> ajax = new HashMap<>();
        ajax.put("code",200);
        ajax.put("msg","操作成功");
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }

}
