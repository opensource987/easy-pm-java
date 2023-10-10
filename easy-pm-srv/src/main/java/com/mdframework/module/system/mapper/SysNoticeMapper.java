package com.mdframework.module.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mdframework.module.system.domain.SysNotice;
import com.mdframework.module.system.domain.vo.SysNoticeVo;
import org.apache.ibatis.annotations.Param;

/**
 * 通知公告表 数据层
 * 
 * @author mdframework
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice>
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    //public List<SysNotice> selectNoticeList(SysNotice notice);
    //public List<SysNotice> selectNoticeList(QueryWrapper<SysNotice> lqw);

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    //public int insertNotice(SysNotice notice);

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    //public int updateNotice(SysNotice notice);

    /**
     * 批量删除公告
     * 
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deleteNoticeById(Long noticeId);

    /**
     * 批量删除公告信息
     * 
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deleteNoticeByIds(Long[] noticeIds);

    List<SysNoticeVo> listVo(@Param("ew") QueryWrapper<SysNotice> lqw);

    int insertNotice(SysNotice notice);
}
