package com.mdframework.aspectj;

import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.module.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定@CurrentMember的方法参数解析器
 */
@Slf4j
@Service
public class CurrentMemberMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private IMemberInfoService memberInfoService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterType().isAssignableFrom(MemberInfo.class)
                && parameter.hasParameterAnnotation(CurrentMember.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        CurrentMember currentUserAnnotation = parameter.getParameterAnnotation(CurrentMember.class);
        String token = webRequest.getHeader("Authorization");
        if (token == null) {
            log.info("从请求参数获取accessToken");
            token = webRequest.getParameter("accessToken");
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        token = token.substring(7);
        MemberInfo memberInfo = this.memberInfoService.getById(Long.valueOf(tokenService.getMemberId(token)));
        return memberInfo;
    }
}
