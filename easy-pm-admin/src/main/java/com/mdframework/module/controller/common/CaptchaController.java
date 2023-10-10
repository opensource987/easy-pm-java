package com.mdframework.module.controller.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.mdframework.common.config.MdframeworkConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Producer;
import com.mdframework.common.constant.Constants;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.core.redis.RedisCache;
import com.mdframework.common.utils.sign.Base64;
import com.mdframework.common.utils.uuid.IdUtils;

/**
 * 验证码操作处理
 * 
 * @author mdframework
 */
@Api(tags = "系统登录")
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;
    
    // 验证码类型

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    @ApiOperation("获取登录验证码")
    public Map getCode(HttpServletResponse response) throws IOException
    {
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        result.put("msg","操作成功");
        // 生成验证码
        if ("math".equals(MdframeworkConfig.getCaptchaType()))
        {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        else if ("char".equals(MdframeworkConfig.getCaptchaType()))
        {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        System.out.println("captcha code = " + capStr);

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            result.put("code",500);
            result.put("msg",e.getMessage());
            return result;
        }


        result.put("uuid", uuid);
        result.put("img", Base64.encode(os.toByteArray()));
        return result;
    }
}
