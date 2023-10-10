package com.mdframework.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mdframework.common.constant.UserConstants;
import com.mdframework.common.exception.CustomException;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.module.system.service.ISysPostService;
import com.mdframework.module.system.domain.SysPost;
import com.mdframework.module.system.domain.vo.SysPostVo;
import com.mdframework.module.system.mapper.SysPostMapper;
import com.mdframework.module.system.mapper.SysUserPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * 岗位信息 服务层处理
 * 
 * @author mdframework
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService
{
    @Autowired
    private SysPostMapper postMapper;

    @Autowired
    private SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     * 
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post)
    {
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(post.getPostCode()),"post_code",post.getPostCode())
            .eq(StringUtils.isNotBlank(post.getStatus()),"status",post.getStatus())
            .like(StringUtils.isNotBlank(post.getPostName()),"post_name",post.getPostName());
        return postMapper.selectPostList(queryWrapper);
        //return postMapper.selectPostList(post);
    }

    /**
     * 查询所有岗位
     * 
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll()
    {
        return postMapper.selectPostAll();
    }

    /**
     * 通过岗位ID查询岗位信息
     * 
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId)
    {
        return postMapper.selectPostById(postId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     * 
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Integer> selectPostListByUserId(Long userId)
    {
        return postMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostNameUnique(post.getPostName());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post)
    {
        Long postId = StringUtils.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost info = postMapper.checkPostCodeUnique(post.getPostCode());
        if (StringUtils.isNotNull(info) && info.getPostId().longValue() != postId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int countUserPostById(Long postId)
    {
        return userPostMapper.countUserPostById(postId);
    }

    /**
     * 删除岗位信息
     * 
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public int deletePostById(Long postId)
    {
        return postMapper.deletePostById(postId);
    }

    /**
     * 批量删除岗位信息
     * 
     * @param postIds 需要删除的岗位ID
     * @return 结果
     * @throws Exception 异常
     */
    @Override
    public int deletePostByIds(Long[] postIds)
    {
        for (Long postId : postIds)
        {
            SysPost post = selectPostById(postId);
            if (countUserPostById(postId) > 0)
            {
                throw new CustomException(String.format("%1$s已分配,不能删除", post.getPostName()));
            }
        }
        return postMapper.deletePostByIds(postIds);
    }

    /**
     * 新增保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int insertPost(SysPost post)
    {
        //Mybatisplus的insert插入失败，用代码实现
        post.setCreateTime(new Date());
        return this.baseMapper.insert(post);
        //return postMapper.insertPost(post);
    }

    /**
     * 修改保存岗位信息
     * 
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public int updatePost(SysPost post)
    {
        UpdateWrapper<SysPost> sysPostUpdateWrapper = new UpdateWrapper<>();
        sysPostUpdateWrapper.set(StringUtils.isNotBlank(post.getPostCode()),"post_code",post.getPostCode())
            .set(StringUtils.isNotBlank(post.getPostName()),"post_name",post.getPostName())
            .set(StringUtils.isNotBlank(post.getPostSort()),"post_sort",post.getPostSort())
            .set(StringUtils.isNotBlank(post.getStatus()),"status",post.getStatus())
            .set(StringUtils.isNotBlank(post.getRemark()),"remark",post.getRemark())
            .set(StringUtils.isNotBlank(post.getUpdateBy()),"update_by",post.getUpdateBy())
            .set("update_time",LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) )
            .eq("post_id",post.getPostId());
        return this.baseMapper.update(null,sysPostUpdateWrapper);
        //return postMapper.updatePost(sysPostUpdateWrapper);
    }

    @Override
    public List<SysPostVo> listVo(QueryWrapper<SysPost> lqw) {
        return this.baseMapper.listVo(lqw);
    }
}
