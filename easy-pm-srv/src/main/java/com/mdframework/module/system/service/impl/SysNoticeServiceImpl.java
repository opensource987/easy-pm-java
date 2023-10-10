package com.mdframework.module.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysNoticeService;
import com.mdframework.module.system.domain.SysNotice;
import com.mdframework.module.system.domain.vo.SysNoticeVo;
import com.mdframework.module.system.mapper.SysNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 公告 服务层实现
 * 
 * @author mdframework
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     * 
     * @param lqw 封装的查询条件
     * @return 公告集合
     */
    @Override
    public List<SysNoticeVo> listVo(QueryWrapper<SysNotice> lqw) {
        return this.baseMapper.listVo(lqw);
    }

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        //Mybatisplus的insert插入CreateTime失败，用代码实现
        //notice.setCreateTime(new Date());
        //return baseMapper.insert(notice);
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        UpdateWrapper<SysNotice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set(StringUtils.isNotBlank(notice.getNoticeTitle()),"notice_title",notice.getNoticeTitle())
        .set(StringUtils.isNotBlank(notice.getNoticeType()),"notice_type",notice.getNoticeType())
        .set(StringUtils.isNotBlank(notice.getNoticeContent()),"notice_content",notice.getNoticeContent())
        .set(StringUtils.isNotBlank(notice.getStatus()),"status",notice.getStatus())
        .set(ObjectUtil.isNotNull(notice.getUpdateBy()),"update_by",notice.getUpdateBy())
        .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
        .eq("notice_id",notice.getNoticeId());
        return baseMapper.update(null,updateWrapper);
        //return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId)
    {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds)
    {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }


}
