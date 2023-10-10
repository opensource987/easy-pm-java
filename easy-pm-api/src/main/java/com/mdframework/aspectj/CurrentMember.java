package com.mdframework.aspectj;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>绑定当前登录的用户</p>
 * <p>不同于@ModelAttribute</p>
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentMember {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default "user";

}
