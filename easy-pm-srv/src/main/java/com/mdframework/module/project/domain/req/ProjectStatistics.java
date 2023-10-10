package com.mdframework.module.project.domain.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectStatistics {
    private static final long serialVersionUID = 1L;
    //新启动项目
    private Integer newStartNum;

    //完结项目
    private Integer closedNum;

    //开发中的项目
    private Integer developingNum;

    //测试中的项目
    private Integer testingNum;

    //待验收项目
    private Integer developedNum;

    //暂停项目
    private Integer stopedNum;

    //取消项目
    private Integer canceledNum;

    //运维项目
    private Integer maintainNum;

    //项目总数
    private Integer totalNum;


}
