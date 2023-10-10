package com.mdframework.module.controller.member;

import com.mdframework.aspectj.CurrentMember;
import com.mdframework.common.exception.DataNotFoundException;
import com.mdframework.module.service.IApiLoginService;
import com.mdframework.module.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mdframework.common.annotation.Log;
import com.mdframework.common.core.controller.BaseController;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.utils.poi.ExcelUtil;
import io.swagger.annotations.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 
 * ==========   ========================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName MemberInfoController
 * @Author mdframework
 * @Date 2020-11-06
 * @Version 1.0.0
 * @Description 会员信息Controller 接口类
 */
@Validated
@Api(tags = "会员信息")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/member/memberInfo" )
public class MemberInfoController extends BaseController {

    private final IMemberInfoService iMemberInfoService;
    private final IApiLoginService iApiLoginService;

    /**
     * 获取用户列表详细信息
     */
    @Log(title = "获取登录信息")
    @ApiOperation("当前登录信息")
    @GetMapping(value = "/getCurrentInfo")
    public AjaxResult<MemberVo> getCurrentInfo(@ApiIgnore @CurrentMember MemberInfo currentMember)
    {
        if (null == currentMember) {
            throw  new DataNotFoundException("会员不存在");
        }
        MemberVo memberVo = iApiLoginService.getMemberVo(currentMember);
        return AjaxResult.success(memberVo);
    }
}
