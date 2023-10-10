package com.mdframework.module.member.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.module.member.mapper.MemberInfoMapper;
import com.mdframework.module.member.domain.MemberInfo;
import com.mdframework.module.member.service.IMemberInfoService;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName MemberInfo
 * @Author mdframework
 * @Date 2020-11-06
 * @Version 1.0.0
 * @Description 会员信息Service业务层处理
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements IMemberInfoService {

}
