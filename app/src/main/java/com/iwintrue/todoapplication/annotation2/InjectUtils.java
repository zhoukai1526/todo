package com.iwintrue.todoapplication.annotation2;

import android.app.Activity;

import com.iwintrue.commonlibrary.utils.L;

/**
 * Created by zhoukai on 2017/7/1.
 */

public class InjectUtils {

    public  static void injectInheit(Activity activity){
        Class a = activity.getClass();
        if(a.isAnnotationPresent(MyInherit.class)){
            MyInherit inherit = (MyInherit) a.getAnnotation(MyInherit.class);
            String value = inherit.value();
            L.i("------------value---------------"+activity.getClass().getSimpleName()+":"+value);
        }
    }

}
