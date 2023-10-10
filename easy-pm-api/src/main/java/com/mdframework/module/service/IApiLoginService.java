package com.mdframework.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.req.MiniAppLoginReq;
import com.mdframework.module.vo.MemberVo;

/**
 * 登录service
 *
 */
public interface IApiLoginService extends IService<MemberInfo> {

    AjaxResult<MemberVo> loginBywechatMiniApp(MiniAppLoginReq miniAppLoginReq)  throws Exception ;

    MemberVo getMemberVo(MemberInfo memberInfo);
}
