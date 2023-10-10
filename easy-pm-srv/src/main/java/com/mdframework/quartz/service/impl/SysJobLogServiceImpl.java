package com.mdframework.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.quartz.domain.SysJobLog;
import com.mdframework.quartz.mapper.SysJobLogMapper;
import com.mdframework.quartz.service.ISysJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 定时任务调度日志信息 服务层
 * 
 * @author mdframework
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper,SysJobLog> implements ISysJobLogService
{
    @Autowired
    private SysJobLogMapper jobLogMapper;

    /**
     * 获取quartz调度器日志的计划任务
     * 
     * @param jobLog 调度日志信息
     * @return 调度任务日志集合
     */
    @Override
    public List<SysJobLog> selectJobLogList(SysJobLog jobLog)
    {
        QueryWrapper<SysJobLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(jobLog.getJobName()),"job_name",jobLog.getJobName());
        queryWrapper.eq(StringUtils.isNotBlank(jobLog.getJobGroup()),"job_group",jobLog.getJobGroup());
        queryWrapper.eq(StringUtils.isNotBlank(jobLog.getStatus()),"status",jobLog.getStatus());
        queryWrapper.ge(StringUtils.isNotBlank(jobLog.getBeginTime()),"date_format(create_time,'%y%m%d)",jobLog.getBeginTime());
        queryWrapper.le(StringUtils.isNotBlank(jobLog.getEndTime()),"date_format(create_time,'%y%m%d)",jobLog.getEndTime());
        return jobLogMapper.selectJobLogList(queryWrapper);
        //return jobLogMapper.selectJobLogList(jobLog);
    }

    /**
     * 通过调度任务日志ID查询调度信息
     * 
     * @param jobLogId 调度任务日志ID
     * @return 调度任务日志对象信息
     */
    @Override
    public SysJobLog selectJobLogById(Long jobLogId)
    {
        return jobLogMapper.selectJobLogById(jobLogId);
    }

    /**
     * 新增任务日志
     * 
     * @param jobLog 调度日志信息
     */
    @Override
    public void addJobLog(SysJobLog jobLog)
    {
        //Mybatisplus的insert插入失败，用代码实现
        jobLog.setCreateTime(new Date());
        baseMapper.insert(jobLog);
        //jobLogMapper.insertJobLog(jobLog);
    }

    /**
     * 批量删除调度日志信息
     * 
     * @param logIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteJobLogByIds(Long[] logIds)
    {
        return jobLogMapper.deleteJobLogByIds(logIds);
    }

    /**
     * 删除任务日志
     * 
     * @param jobId 调度日志ID
     */
    @Override
    public int deleteJobLogById(Long jobId)
    {
        return jobLogMapper.deleteJobLogById(jobId);
    }

    /**
     * 清空任务日志
     */
    @Override
    public void cleanJobLog()
    {
        jobLogMapper.cleanJobLog();
    }
}
