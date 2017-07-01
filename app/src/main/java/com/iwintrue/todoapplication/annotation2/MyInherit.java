package com.iwintrue.todoapplication.annotation2;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhoukai on 2017/7/1.
 */

//自定义注解
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyInherit  {
    String value() default "aaa";
    enum Color{red,yello,green}
    public Color getColor() default Color.green;
}
