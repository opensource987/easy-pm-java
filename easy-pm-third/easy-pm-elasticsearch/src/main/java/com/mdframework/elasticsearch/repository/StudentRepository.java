package com.mdframework.elasticsearch.repository;

import com.mdframework.elasticsearch.domain.Student;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName StudentRepository
 * @Author Jesse(573110463 @ qq.com)
 * @Date 2021-05-26 15:32 星期三
 * @Version 1.0
 * @Description ${TODO}
 */
public interface StudentRepository extends ElasticsearchRepository<Student, String> {

    List<Student> findByNameLike(String keyword);
    /**
     * 自定义查询，固定匹配查询学生信息
     * @param keyword
     * @return
     */
    @Query("{\"match_phrase\":{\"name\":\"?0\"}}")
    List<Student> findByNameCustom(String keyword);

}
