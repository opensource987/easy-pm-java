package com.mdframework.module.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mdframework.common.core.domain.BasePlusEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.mdframework.common.core.domain.BaseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知公告表 sys_notice
 * 
 * @author mdframework
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_notice")
public class SysNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 公告标题 */
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    private String status;

    ////备注
    //private String remark;
    /** 请求参数 */
    //@TableField(exist = false)
    //private Map<String, Object> params;

    public Long getNoticeId()
    {
        return noticeId;
    }

    public void setNoticeId(Long noticeId)
    {
        this.noticeId = noticeId;
    }

    public void setNoticeTitle(String noticeTitle)
    {
        this.noticeTitle = noticeTitle;
    }

    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    public String getNoticeTitle()
    {
        return noticeTitle;
    }

    public void setNoticeType(String noticeType)
    {
        this.noticeType = noticeType;
    }

    public String getNoticeType()
    {
        return noticeType;
    }

    public void setNoticeContent(String noticeContent)
    {
        this.noticeContent = noticeContent;
    }

    public String getNoticeContent()
    {
        return noticeContent;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

/*    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }*/
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("noticeId", getNoticeId())
            .append("noticeTitle", getNoticeTitle())
            .append("noticeType", getNoticeType())
            .append("noticeContent", getNoticeContent())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
