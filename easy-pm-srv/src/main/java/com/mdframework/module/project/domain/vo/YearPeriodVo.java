package com.mdframework.module.project.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdframework.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class YearPeriodVo {
    private static final long serialVersionUID = 1L;

    private Date beginTime;
    private Date endTime;
}
