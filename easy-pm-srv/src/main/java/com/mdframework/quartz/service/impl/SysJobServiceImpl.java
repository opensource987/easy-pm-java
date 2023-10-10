package com.mdframework.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.constant.ScheduleConstants;
import com.mdframework.common.exception.job.TaskException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.quartz.domain.SysJob;
import com.mdframework.quartz.mapper.SysJobMapper;
import com.mdframework.quartz.service.ISysJobService;
import com.mdframework.quartz.util.CronUtils;
import com.mdframework.quartz.util.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 定时任务调度信息 服务层
 * 
 * @author mdframework
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper,SysJob> implements ISysJobService
{
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException
    {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
    }

    /**
     * 获取quartz调度器的计划任务列表
     * 
     * @param job 调度信息
     * @return
     */
    @Override
    public List<SysJob> selectJobList(SysJob job)
    {
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(job.getJobName()),"job_name",job.getJobName());
        queryWrapper.eq(StringUtils.isNotBlank(job.getJobGroup()),"job_group",job.getJobGroup());
        queryWrapper.eq(StringUtils.isNotBlank(job.getStatus()),"status",job.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(job.getInvokeTarget()),"invoke_target",job.getInvokeTarget());
        return jobMapper.selectJobList(queryWrapper);
        //return jobMapper.selectJobList(job);
    }

    /**
     * 通过调度任务ID查询调度信息
     * 
     * @param jobId 调度任务ID
     * @return 调度任务对象信息
     */
    @Override
    public SysJob selectJobById(Long jobId)
    {
        return jobMapper.selectJobById(jobId);
    }

    /**
     * 暂停任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int pauseJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = updateQueryJob(job);
        //int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int resumeJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = updateQueryJob(job);
        //int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 删除任务后，所对应的trigger也将被删除
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int deleteJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        int rows = jobMapper.deleteJobById(jobId);
        if (rows > 0)
        {
            scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 批量删除调度信息
     * 
     * @param jobIds 需要删除的任务ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteJobByIds(Long[] jobIds) throws SchedulerException
    {
        for (Long jobId : jobIds)
        {
            SysJob job = jobMapper.selectJobById(jobId);
            deleteJob(job);
        }
    }

    /**
     * 任务调度状态修改
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int changeStatus(SysJob job) throws SchedulerException
    {
        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 立即运行任务
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public void run(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        SysJob properties = selectJobById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }

    /**
     * 新增任务
     * 
     * @param job 调度信息 调度信息
     */
    @Override
    @Transactional
    public int insertJob(SysJob job) throws SchedulerException, TaskException
    {
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        //Mybatisplus的insert插入失败，用代码实现
        job.setCreateTime(new Date());
        int rows = baseMapper.insert(job);
        //int rows = jobMapper.insertJob(job);
        if (rows > 0)
        {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        return rows;
    }

    /**
     * 更新任务的时间表达式
     * 
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int updateJob(SysJob job) throws SchedulerException, TaskException
    {
        SysJob properties = selectJobById(job.getJobId());
        int rows = updateQueryJob(job);
        //int rows = jobMapper.updateJob(job);
        if (rows > 0)
        {
            updateSchedulerJob(job, properties.getJobGroup());
        }
        return rows;
    }

    /**
     * 更新任务
     * 
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException
    {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey))
        {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    /**
     * 校验cron表达式是否有效
     * 
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression)
    {
        return CronUtils.isValid(cronExpression);
    }

    public int updateQueryJob(SysJob job){
        UpdateWrapper<SysJob> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtils.isNotBlank(job.getJobName()),"job_name",job.getJobName());
        updateWrapper.set(StringUtils.isNotBlank(job.getJobGroup()),"job_group",job.getJobGroup());
        updateWrapper.set(StringUtils.isNotBlank(job.getInvokeTarget()),"invoke_target",job.getInvokeTarget());
        updateWrapper.set(StringUtils.isNotBlank(job.getCronExpression()),"cron_expression",job.getCronExpression());
        updateWrapper.set(StringUtils.isNotBlank(job.getMisfirePolicy()),"misfire_policy",job.getMisfirePolicy());
        updateWrapper.set(StringUtils.isNotBlank(job.getConcurrent()),"concurrent",job.getConcurrent());
        updateWrapper.set(StringUtils.isNotBlank(job.getStatus()),"status",job.getStatus());
        updateWrapper.set(StringUtils.isNotBlank(job.getRemark()),"remark",job.getRemark());
        updateWrapper.set(StringUtils.isNotBlank(job.getUpdateBy()),"update_by",job.getUpdateBy());
        String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        updateWrapper.set("update_time",timeStr1);
        updateWrapper.eq("job_id",job.getJobId());
        return baseMapper.update(null,updateWrapper);
    }
}
