package com.iwintrue.todoapplication.annotation2;

import android.app.Activity;
import android.os.Bundle;

import com.iwintrue.commonlibrary.utils.L;

/**
 * Created by zhoukai on 2017/7/1.
 */
@MyInherit()
public class Parent extends Activity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.injectInheit(this);
        L.i("--------------"+getClass().getSimpleName());
        L.i("--------------"+getClass().getSimpleName());
        L.i("--------------"+getClass().getSimpleName());
        L.i("--------------"+getClass().getSimpleName());

    }
}



