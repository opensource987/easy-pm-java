package com.mdframework.module.controller.login;

import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.message.AliyunMessage;
import com.mdframework.module.req.MiniAppLoginReq;
import com.mdframework.module.service.IApiLoginService;
import com.mdframework.module.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName LoginController
 * @Author Jesse(aa573110463@qq.com)
 * @Date 2020-11-06 11:00:00 星期五
 * @Version 1.0.0
 * @Description 登录相关
 */
@RequestMapping("/outer")
@Api(tags = "登录相关")
@RestController
public class LoginController {

    @Autowired
    private IApiLoginService apiLoginService;

    /**
     * 小程序授权
     * @param miniAppLoginReq
     * @return
     */
    @ApiOperation(value = "小程序登陆")
    @PostMapping("/loginBywechatMiniApp")
    public AjaxResult<MemberVo> loginBywechatMiniApp(@RequestBody @Valid MiniAppLoginReq miniAppLoginReq) throws Exception  {
        return apiLoginService.loginBywechatMiniApp(miniAppLoginReq);
    }

    @Resource
    private AliyunMessage aliyunMessage;
    /**
     * 小程序授权
     * @return
     */
    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParam(name = "mobile",value = "手机号码",required = true,paramType = "query")
    @PostMapping("/sendMessageCode")
    public AjaxResult sendMessageCode(@RequestParam(name = "mobile") String mobile) throws Exception  {

        this.aliyunMessage.alidayuCheckCode(mobile,"5684");

        return AjaxResult.success();
    }

}
