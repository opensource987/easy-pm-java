package com.mdframework.module.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.req.MiniAppLoginReq;
import com.mdframework.module.service.IApiLoginService;
import com.mdframework.module.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
@Api(tags = "test")
@RestController
public class FirstController {

    @Autowired
    private IApiLoginService apiLoginService;

    /**
     * 小程序授权
     * @return
     */
    @ApiOperation(value = "小程序登陆")
    @RequestMapping("/")
    public void loginBywechatMiniApp(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestBody String body) throws IOException {
        String requestCode =  request.getHeader("request_code");
//        response.setContentType("application/octet-stream");
        response.setHeader("response_code","OK");
        response.setHeader("trans_id",request.getHeader("trans_id"));
        if (StringUtils.isNoneBlank(requestCode) && "receive_cmd".equalsIgnoreCase(requestCode)) {
            //心跳
            System.out.println("心跳包,dev_id=" + request.getHeader("dev_id"));
//            System.out.println("body:" +body);
        } else {
            System.out.println("其他数据包："+ requestCode);
            if(StringUtils.isNoneBlank(requestCode) && "realtime_glog".equalsIgnoreCase(requestCode)) {
                //下发修改用户姓名
                Enumeration<String> names = request.getHeaderNames();
                System.out.println("-----头开始");
                while(names.hasMoreElements()) {
                    String name = names.nextElement();
                    System.out.println(name + ":" + request.getHeader(name));
                }
                System.out.println("-----头结束");
                response.setHeader("cmd_code","SET_USER_NAME");//指令标志
                Map<String,Object> map = new HashMap<>();
                map.put("user_id","1");
                map.put("user_name","方益志");
                String json = JSONUtil.toJsonStr(map);
                PrintWriter out = response.getWriter();
                out.write(json);
                out.close();
                System.out.println("人脸识别数据:" + body);


            }
        }
    }

    public static void main(String[] args) {
//        String a = "z...{\"fk_bin_data_lib\":\"FKDataHS101\",\"io_mode\":1,\"io_time\":\"20210104174003\",\"log_image\":null,\"user_id\":\"1\",\"verify_mode\":20}";
//        System.out.println(a.length());

        System.loadLibrary("classpath:MCACS.dll");



    }

}
