package com.mdframework.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 处理新增和更新的基础数据填充，配合BaseEntity和MyBatisPlusConfig使用
 *
 * @author Carlos
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    /**
     * 新增数据执行
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy", "0", metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", "0", metaObject);
    }

    /**
     * 更新数据执行
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy", "0", metaObject);
    }

}
