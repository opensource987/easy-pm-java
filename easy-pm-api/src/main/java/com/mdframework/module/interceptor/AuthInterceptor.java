package com.mdframework.module.interceptor;

import cn.hutool.json.JSONUtil;
import com.mdframework.common.constant.HttpStatus;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.module.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于接口权限拦截校验
 *
 * @author Jax
 */
//@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private final static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    private static final String SECURITY_SIGN_SET = "security_sign_set:";
    public static final String AUTHORIZATION = "Authorization";

    private IMemberInfoService memberInfoService;
    private TokenService tokenService;

//	private MemberService memberService;
//
	public AuthInterceptor(IMemberInfoService memberInfoService, TokenService tokenService) {
		this.memberInfoService = memberInfoService;
		this.tokenService = tokenService;
	}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.info("当前请求接口：{}, 请求方式：{}", request.getRequestURI(), request.getMethod());

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, Object> headerMap = new HashMap<>();
        StringBuffer sb = new StringBuffer();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement().toString();
            String value = request.getHeader(name);
            sb.append(name).append(":").append(value).append("\n");
            headerMap.put(name, value);
        }
        log.info(sb.toString());

        if (checkIgnore(request.getRequestURI())) {
            return true;
        }

        if (!securityCheck(request, response)) {
            responseOutJson(response,AjaxResult.error(HttpStatus.FORBIDDEN, "登录状态已失效,请重新登录"));
            return false;
        }
        return true;
    }

    private boolean checkIgnore(String uri) {
        if (uri.equals("/v1/outer/token")
                || uri.equals("/v1/outer/tokenByOpenid")
                || uri.startsWith("/swagger-resources")
                || uri.startsWith("/webjars") )
         {
            return true;
        }
        return false;
    }

    private boolean securityCheck(HttpServletRequest request, HttpServletResponse response) {
        String tokenHeader = AUTHORIZATION;
        String accessToken = request.getHeader(tokenHeader) == null ? "" : request.getHeader(tokenHeader).toString();
        if (StringUtils.isBlank(accessToken)) {
            return false;
        }
        String authStr = accessToken.substring(7);
        // JWT 校验
        boolean flag = false;

        String memberId = tokenService.getMemberId(authStr);
        MemberInfo memberInfo = memberInfoService.getById(Integer.valueOf(memberId));
        if (null == memberInfo) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }


    private void responseOutJson(HttpServletResponse response, Object obj) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("", "");
        response.setHeader("", "");

        PrintWriter out = null;
        String json = null;
        try {
            json = JSONUtil.toJsonStr(obj);
            out = response.getWriter();
            out.write(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
