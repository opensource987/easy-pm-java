package com.mdframework.module.system.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysOperLogService;
import com.mdframework.module.system.domain.SysOperLog;
import com.mdframework.module.system.mapper.SysOperLogMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 操作日志 服务层处理
 * 
 * @author mdframework
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService
{
    @Autowired
    private SysOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog)
    {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     * 
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog)
    {

        QueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(operLog.getTitle()),"title",operLog.getTitle())
            .eq(ObjectUtil.isNotNull(operLog.getBusinessType()),"business_type",operLog.getBusinessType())
            .in(ArrayUtil.isNotEmpty(operLog.getBusinessTypes()),"business_type",operLog.getBusinessTypes())
            .eq(ObjectUtil.isNotNull(operLog.getStatus()),"status",operLog.getStatus())
            .like(StringUtils.isNotBlank(operLog.getOperName()),"oper_name",operLog.getOperName())
            .ge(StringUtils.isNotBlank(operLog.getBeginTime()),"date_format(oper_time,'%y%m%d')",operLog.getBeginTime())
            .le(StringUtils.isNotBlank(operLog.getEndTime()),"date_format(oper_time,'%y%m%d')",operLog.getEndTime())
            .orderByDesc("oper_id");
        return operLogMapper.selectOperLogList(queryWrapper);
        //return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     * 
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOperLogByIds(Long[] operIds)
    {
        return operLogMapper.deleteOperLogByIds(operIds);
    }

    /**
     * 查询操作日志详细
     * 
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId)
    {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog()
    {
        operLogMapper.cleanOperLog();
    }
}
