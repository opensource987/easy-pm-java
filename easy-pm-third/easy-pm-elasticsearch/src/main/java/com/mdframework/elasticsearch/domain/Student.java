package com.mdframework.elasticsearch.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName Student
 * @Author Jesse(573110463 @ qq.com)
 * @Date 2021-05-26 15:26 星期三
 * @Version 1.0
 * @Description ${TODO}
 */

@Data
@Accessors(chain = true)
@Document(indexName = "student")
public class Student {

    private static final long serialVersionUID = 1L;
 
    @Id
    private String id;
 
    private String name;
 
    private String gender;
 
    private Integer age;
}
