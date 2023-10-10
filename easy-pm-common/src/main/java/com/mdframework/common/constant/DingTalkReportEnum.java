package com.mdframework.common.constant;

import lombok.Data;

public enum DingTalkReportEnum {

    //加班
    OVERTIME("52076966","加班总时长","overtime_duration"),
    LATE("10617315","迟到次数","late_times"),
    LATE_MINUTE("10617316","迟到时长","late_minute"),
    ATTENDANCE_TIME("10617314","工作时长","attendance_work_time"),
    SHI_JIA("事假","事假","shi_jia"),
    TIAO_XIU("调休","调休","tiao_xiu");

    //报表列值id
    private String id;
    //报表列值名
    private String name;
    //报表列值别名
    private String alias;

    DingTalkReportEnum(String id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
