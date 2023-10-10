package com.mdframework.module.project.domain;

import lombok.Data;

import java.util.List;

@Data
public class ProjectModuleExcel {
    private String projectName;
    private List<ProjectModuleNode> moduleList;
}
