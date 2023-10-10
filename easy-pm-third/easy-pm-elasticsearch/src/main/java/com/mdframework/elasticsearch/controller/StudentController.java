package com.mdframework.elasticsearch.controller;

import com.mdframework.common.core.domain.AjaxResult;
import com.mdframework.common.utils.StringUtils;
import com.mdframework.elasticsearch.domain.Student;
import com.mdframework.elasticsearch.repository.StudentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 
 * ==================================================================

 * 这不是一个自由软件！未经本公司授权您只能在不用于商业目的的前提下
 * 对本程序代码进行修改和使用；不允许对本程序代码以任何目的的再发布。
 * ==================================================================
 *
 * @ClassName StudentController
 * @Author Jesse(573110463 @ qq.com)
 * @Date 2021-05-26 15:35 星期三
 * @Version 1.0
 * @Description ${TODO}
 */
@Api(tags = "学生相关")
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
      * 批量添加
      * @param students
      * @return
      */
    @ApiOperation(value = "批量添加")
    @PostMapping("/batchAdd")
    public AjaxResult add(@RequestBody List<Student> students){
        studentRepository.saveAll(students);
        return AjaxResult.success();
    }
 
    /**
      * 添加
      * @param student
      * @return
      */
    @ApiOperation(value = "添加")
            @PostMapping("/add")
    public AjaxResult add(@RequestBody Student student){
//        elasticsearchRestTemplate.queryForObject()
        studentRepository.save(student);
        return AjaxResult.success();
    }
 
    /**
      * 修改
      * @param student
      * @return
      */
    @ApiOperation(value = "修改")
            @PostMapping("/update")
    public AjaxResult updateById(@RequestBody Student student){
        studentRepository.save(student);
        return AjaxResult.success();
    }
 
    /**
      * 删除
      * @param id
      * @return
      */
    @ApiOperation(value = "删除")
            @PostMapping("/delete/{id}")
    public AjaxResult deleteById(@PathVariable String id){
        studentRepository.deleteById(id);
        return AjaxResult.success();
    }
 
    /**
      * 获取所有信息
      * @return
      */
    @ApiOperation(value = "获取所有信息")
            @GetMapping("/get")
    public AjaxResult<List<Student>> getAll(){


        Iterable<Student> iterable = studentRepository.findAll();
        List<Student> list = new ArrayList<>();
        iterable.forEach(list :: add);


        return AjaxResult.success(list);
    }
 
    /**
      * 查询指定ID
      * @param id
      * @return
      */
    @ApiOperation(value = "查询指定ID")
            @GetMapping("/get/{id}")
    public AjaxResult<Student> getById(@PathVariable String id){
        if(StringUtils.isEmpty(id)){
            return AjaxResult.error();
        }
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isPresent()){
            return AjaxResult.success(studentOptional.get());
        }
        return null;
    }
 
 
    /**
      * 普通搜索
      * @param keyword
      * @return
      */
    @ApiOperation(value = "普通搜索")
    @GetMapping("/search/name")
    public AjaxResult<List<Student>> searchName(String keyword){
        List<Student> students = studentRepository.findByNameLike(keyword);
        return AjaxResult.success(students);
    }
 
    /**
      * 自定义匹配
      * 普通搜索
      * @param keyword
      * @return
      */
    @ApiOperation(value = "自定义匹配")
            @GetMapping("/search/name/custom")
    public AjaxResult<List<Student>> searchTitleCustom(String keyword){
        List<Student> students = studentRepository.findByNameCustom(keyword);
        return AjaxResult.success(students);
    }
 
}
