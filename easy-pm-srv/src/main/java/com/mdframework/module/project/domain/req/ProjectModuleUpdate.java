package com.mdframework.module.project.domain.req;

import com.mdframework.module.system.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName ProjectModuleUpdate
 * @Author mdframework
 * @Date 2021-09-14
 * @Version 1.0.0
 * @Description 项目模块更新请求体对象
 */
@Data
@ToString
@Validated
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectModuleUpdate extends ProjectModuleAdd {

    @NotNull(message = "自增id不能为空")
    @ApiModelProperty(value = "自增id",required = true)
    private Long id;

    private List<SysUser> users;
}
