package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysLogininforService;
import com.mdframework.module.system.domain.SysLogininfor;
import com.mdframework.module.system.mapper.SysLogininforMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author mdframework
 */
@Service
public class SysLogininforServiceImpl extends ServiceImpl<SysLogininforMapper, SysLogininfor> implements ISysLogininforService
{

    @Autowired
    private SysLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     * 
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor)
    {
            QueryWrapper<SysLogininfor> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(StringUtils.isNotBlank(logininfor.getIpaddr()),"ipaddr",logininfor.getIpaddr())
                .eq(StringUtils.isNotBlank(logininfor.getStatus()),"status",logininfor.getIpaddr())
                .like(StringUtils.isNotBlank(logininfor.getUserName()),"user_name",logininfor.getUserName())
                .like(logininfor.getLoginTime()!=null,"date_format(login_time,'%y%m%d')",logininfor.getIpaddr())
                .like(logininfor.getEndTime()!=null,"date_format(login_time,'%y%m%d')",logininfor.getIpaddr())
                .orderByDesc("info_id");
        return logininforMapper.selectLogininforList(queryWrapper);
    }

    /**
     * 批量删除系统登录日志
     * 
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds)
    {
        return logininforMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }

}
