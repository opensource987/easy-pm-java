package com.mdframework.module.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.utils.SLEmojiFilter;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.mapper.MemberInfoMapper;
import com.mdframework.module.member.service.IMemberInfoService;
import com.mdframework.module.req.MiniAppLoginReq;
import com.mdframework.module.service.IApiLoginService;
import com.mdframework.module.service.TokenService;
import com.mdframework.module.vo.MemberVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * loginService 服务层实现
 */
@Service
public class ApiLoginServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements IApiLoginService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxMaService wxService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult<MemberVo> loginBywechatMiniApp(MiniAppLoginReq miniAppLoginReq) throws Exception {
        WxMaUserService wxMaUserService = this.wxService.getUserService();
        WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaUserService.getSessionInfo(miniAppLoginReq.getCode());
        WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(wxMaJscode2SessionResult.getSessionKey(),
                miniAppLoginReq.getEncryptedData(),
                miniAppLoginReq.getIv()
                );
        wxMaUserInfo.setOpenId(wxMaJscode2SessionResult.getOpenid());
        wxMaUserInfo.setUnionId(wxMaJscode2SessionResult.getUnionid());
        //查询数据库是否存在 openid的会员  或者unionid的会员
        QueryWrapper<MemberInfo> qw = new QueryWrapper<>();
        qw.eq("wx_mini_open_id",wxMaUserInfo.getOpenId());
        //        qw.eq("wx_union_id", wxMaUserInfo.getUnionId());
        MemberInfo member =  getOne(qw,false);
        if (null == member) {
            //新会员
            member = new MemberInfo();
            member.setSource(1);
            member.setSex((StringUtils.isBlank(wxMaUserInfo.getGender())?0:Integer.valueOf(wxMaUserInfo.getGender())));
            member.setNickname(SLEmojiFilter.filterEmoji(wxMaUserInfo.getNickName()));
            member.setName(SLEmojiFilter.filterEmoji(wxMaUserInfo.getNickName()));
            member.setPic(wxMaUserInfo.getAvatarUrl());
            member.setWxUnionId(wxMaUserInfo.getUnionId());
            member.setWxMiniOpenId(wxMaUserInfo.getOpenId());
        } else {
            //原先已在库里面的会员
            member.setNickname(SLEmojiFilter.filterEmoji(wxMaUserInfo.getNickName()));
            member.setName(SLEmojiFilter.filterEmoji(wxMaUserInfo.getNickName()));
            member.setPic(wxMaUserInfo.getAvatarUrl());
        }
        //添加或修改会员信息
        this.saveOrUpdate(member);
        member = this.getById(member.getId());
        MemberVo memberVo= getMemberVo(member);
        return AjaxResult.success(memberVo);
    }

    /**
     * 生成memberVO
     * @param memberInfo
     * @return
     */
    @Override
    public MemberVo getMemberVo(MemberInfo memberInfo) {
        MemberVo memberVo = new MemberVo();
        BeanUtils.copyProperties(memberInfo,memberVo);
        String token = tokenService.createToken(memberInfo);
        memberVo.setToken("Bearer ".concat(token));
        return memberVo;
    }
}