package com.mdframework.module.controller.member;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dtflys.forest.Forest;
import com.mdframework.common.constant.Constants;
import com.mdframework.common.core.redis.RedisCache;
import com.mdframework.common.exception.ParamException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.member.domain.vo.MemberInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.mdframework.common.annotation.Log;
import com.mdframework.common.core.controller.BaseController;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.enums.BusinessType;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.domain.req.MemberInfoReq;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.utils.poi.ExcelUtil;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.*;
import com.mdframework.common.core.page.TableDataInfo;
/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName MemberInfoController
 * @Author mdframework
 * @Date 2020-11-06
 * @Version 1.0.0
 * @Description 会员信息Controller
 */
@Validated
@Api(tags = "会员信息")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/member/memberInfo" )
public class MemberInfoController extends BaseController {

    private final IMemberInfoService iMemberInfoService;

    @Autowired
    private RedisCache redisCache;

 /**
  * @Description 分页
  * @Author mdframework
  * @Date 2020-11-06
  * @Param memberInfo
  * @Return TableDataInfo
 */
    @ApiOperation("列表")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:list')")
    @GetMapping("/list")
    public TableDataInfo<MemberInfoVo> list(MemberInfoReq memberInfoReq)
    {
        startPage();
        QueryWrapper<MemberInfo> lqw = new QueryWrapper<MemberInfo>();
        memberInfoReq.generatorQuery(lqw);
        List<MemberInfo> list = iMemberInfoService.list(lqw);

        List<MemberInfoVo> listMember = Convert.convert(new TypeReference<List<MemberInfoVo>>() {}, list);

        return getDataTable(listMember);
    }

    /**
     * @Description 导出
     * @Author mdframework
     * @Date 2020-11-06
     * @Param memberInfo
     * @Return TableDataInfo
     */
    @ApiOperation("导出")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:export')" )
    @Log(title = "导出会员信息" , businessType = BusinessType.EXPORT)
    @GetMapping("/export" )
    public AjaxResult export(MemberInfoReq memberInfoReq) {
        QueryWrapper<MemberInfo> lqw = new QueryWrapper<MemberInfo>();
        memberInfoReq.generatorQuery(lqw);
        List<MemberInfo> list = iMemberInfoService.list(lqw);
        ExcelUtil<MemberInfo> util = new ExcelUtil<MemberInfo>(MemberInfo. class);
        return util.exportExcel(list, "memberInfo" );
    }

    /**
     * @Description 详情
     * @Author mdframework
     * @Date 2020-11-06
     * @Param memberInfo
     * @Return TableDataInfo
     */
    @ApiOperation("详情")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:query')" )
    @GetMapping(value = "/{id}" )
    public AjaxResult<MemberInfo> getInfo(@PathVariable("id" ) Long id) {
        return AjaxResult.success(iMemberInfoService.getById(id));
    }

    /**
     * @Description 新增
     * @Author mdframework
     * @Date 2020-11-06
     * @Param memberInfo
     * @Return TableDataInfo
     */
    @ApiOperation("新增")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:add')" )
    @Log(title = "添加会员信息" , businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Valid @RequestBody MemberInfo memberInfo) {
        return toAjax(iMemberInfoService.save(memberInfo) ? 1 : 0);
    }

    /**
     * @Description 修改
     * @Author mdframework
     * @Date 2020-11-06
     * @Param memberInfo
     * @Return TableDataInfo
     */
    @ApiOperation("修改")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:edit')" )
    @Log(title = "修改会员信息" , businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Valid @RequestBody MemberInfo memberInfo) {
        if (null == memberInfo.getId()) {
            throw new ParamException("id不能为空");
        }
        return toAjax(iMemberInfoService.updateById(memberInfo) ? 1 : 0);
    }

    /**
     * @Description 删除
     * @Author mdframework
     * @Date 2020-11-06
     * @Param memberInfo
     * @Return TableDataInfo
     */
    @ApiOperation("删除")
    @PreAuthorize("@ss.hasPermi('member:memberInfo:remove')" )
    @Log(title = "删除会员信息" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}" )
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(iMemberInfoService.removeByIds(Arrays.asList(ids)) ? 1 : 0);
    }

    @ApiOperation(value = "发送短信验证码")
    @ApiImplicitParam(name = "mobile",value = "手机号码",required = true,paramType = "query")
    @PostMapping("/sendMessageCode")
    public AjaxResult sendMessageCode(@RequestParam(name = "mobile") String mobile) {

        //防止重复发送
        String verifyCode = redisCache.getCacheObject(Constants.AlREADY_SEND_SMS_CODE_KEY+"_"+mobile);
        if(StringUtils.isNotEmpty(verifyCode)){
            return AjaxResult.error("请勿重复发送");
        }
        verifyCode = String.valueOf((int)(Math.random()*9000+1000));

        String result = Forest.get("https://smsjm.jxtebie.com/sms/submit?spid=615645&password=JChN4Vle&ac=1069615645&mobiles="
                +mobile+
                "&content=【觅音星球】验证码："
                +verifyCode+
                "，本验证码有效时间5分钟，请勿告知他人。").executeAsString();
        System.out.printf(result);
        if(StringUtils.contains(result, "<result>0</result>")){
            redisCache.setCacheObject(Constants.SMS_CODE_KEY+"_"+mobile, verifyCode, Constants.SMS_EXPIRATION, TimeUnit.MINUTES);
            //防止重复发送
            redisCache.setCacheObject(Constants.AlREADY_SEND_SMS_CODE_KEY+"_"+mobile, verifyCode, 1, TimeUnit.MINUTES);
            return AjaxResult.success("发送成功");
        }else{
            return AjaxResult.error("发送短信失败");
        }
    }

    @ApiOperation(value = "短信验证码注册")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "mobile",value = "手机号码",required = true,paramType = "query"),
                    @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "query",required = true),
                    @ApiImplicitParam(name = "pic",value = "用户头像",paramType = "query",required = true),
                    @ApiImplicitParam(name = "nickname",value = "用户昵称",paramType = "query",required = true),
                    @ApiImplicitParam(name = "sex",value = "性别 = 0男，1女，2未知",paramType = "query",required = true),
            }
    )
    @PostMapping("/smsRegister")
    public AjaxResult smsRegister(@NotBlank @RequestParam(name = "mobile") String mobile,
                                  @NotBlank @RequestParam(name = "code") String code,
                                  @NotBlank @RequestParam(name = "pic") String pic,
                                  @NotBlank @RequestParam(name = "nickname") String nickname,
                                  @NotBlank @RequestParam(name = "sex") Integer sex) {

        String verifyCode = redisCache.getCacheObject(Constants.SMS_CODE_KEY+"_"+mobile);
        if(StringUtils.isNotEmpty(verifyCode) && StringUtils.isNotEmpty(code) && verifyCode.equals(code)){
            MemberInfo memberInfo = iMemberInfoService.getOne(Wrappers.<MemberInfo>lambdaQuery()
                    .eq(MemberInfo::getMobile, mobile));
            if(memberInfo != null && memberInfo.getId() !=null){
                return AjaxResult.error("您已经注册过了，请直接登录！");
            }
            //保存账号
            memberInfo = new MemberInfo();
            memberInfo.setMobile(mobile);
            memberInfo.setNickname(nickname);
            memberInfo.setPic(pic);
            memberInfo.setSex(sex);
            memberInfo.setSource(2);  //注册来源为`App`
            iMemberInfoService.save(memberInfo);
            return AjaxResult.success("注册成功", memberInfo);
        }else{
            return AjaxResult.error("验证码失效或错误");
        }
    }

    @ApiOperation(value = "短信验证码登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "mobile",value = "手机号码",required = true,paramType = "query"),
                    @ApiImplicitParam(name = "code",value = "短信验证码",paramType = "query",required = true),
            }
    )
    @PostMapping("/smsLogin")
    public AjaxResult smsLogin(@NotBlank @RequestParam(name = "mobile") String mobile, @NotBlank @RequestParam(name = "code") String code) {

        //审核测试账号固定验证码
        if(mobile.equals("15392717006") && code.equals("0765")){
            final MemberInfo memberInfo = iMemberInfoService.getOne(Wrappers.<MemberInfo>lambdaQuery()
                    .eq(MemberInfo::getMobile, mobile));
            return AjaxResult.success("登录成功", memberInfo);
        }

        String verifyCode = redisCache.getCacheObject(Constants.SMS_CODE_KEY+"_"+mobile);
        if(StringUtils.isNotEmpty(verifyCode) && StringUtils.isNotEmpty(code) && verifyCode.equals(code)){
            final MemberInfo memberInfo = iMemberInfoService.getOne(Wrappers.<MemberInfo>lambdaQuery()
                            .eq(MemberInfo::getMobile, mobile));
            if(memberInfo != null && memberInfo.getId() !=null){
                return AjaxResult.success("登录成功", memberInfo);
            }else{
                return AjaxResult.error("账户不存在");
            }
        }else{
            return AjaxResult.error("验证码错误");
        }
    }

    @ApiOperation(value = "根据手机获取用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "mobile",value = "手机号码",required = true,paramType = "query"),
            }
    )
    @PostMapping("/getMemberByMobile")
    public AjaxResult getMemberByMobile(@NotBlank @RequestParam(name = "mobile") String mobile) {

        final MemberInfo memberInfo = iMemberInfoService.getOne(Wrappers.<MemberInfo>lambdaQuery()
                .eq(MemberInfo::getMobile, mobile));
        if(memberInfo != null && memberInfo.getId() !=null){
            return AjaxResult.success("请求成功", memberInfo);
        }else{
            return AjaxResult.error("账户不存在");
        }

    }

    @ApiOperation(value = "根据手机修改用户信息")
    @PostMapping("/updateMemberByMobile")
    public AjaxResult updateMemberByMobile(@Valid @RequestBody MemberInfo memberInfo) {

        final MemberInfo currentMemberInfo = iMemberInfoService.getOne(Wrappers.<MemberInfo>lambdaQuery()
                .eq(MemberInfo::getMobile, memberInfo.getMobile()));
        if(currentMemberInfo != null && currentMemberInfo.getId() !=null){
            memberInfo.setId(currentMemberInfo.getId());
            iMemberInfoService.updateById(memberInfo);
            return AjaxResult.success("修改成功", memberInfo);
        }else{
            return AjaxResult.error("账户不存在");
        }

    }
}
