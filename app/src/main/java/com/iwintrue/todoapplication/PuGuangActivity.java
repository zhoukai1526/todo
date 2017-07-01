package com.iwintrue.todoapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

public class PuGuangActivity extends Activity {

    private TextView tv10;
    private TextView tv11;
    private TextView tv12;
    private MyScrollView sl;
    private TextView tv1;
    private TextView tv13;
    private TextView tv14;
    private TextView tv20;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pu_guang);
        initView();
    }

    private void initView() {
        sl = (MyScrollView) findViewById(R.id.scoll);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv10 = (TextView) findViewById(R.id.tv10);
        tv11 = (TextView) findViewById(R.id.tv11);
        tv12 = (TextView) findViewById(R.id.tv12);
        tv13 = (TextView) findViewById(R.id.tv13);
        tv14 = (TextView) findViewById(R.id.tv14);
        tv20 = (TextView) findViewById(R.id.tv20);
        WebView web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.loadUrl("https://dljk.weshare.com.cn/regisiter.html?c=32400");


        count = 0;
        sl.setOnScrollChanged(new MyScrollView.OnScrollChanged() {
            @Override
            public void onScroll( int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                Log.i("main","---------scrollX:"+scrollX);
                Log.i("main","---------scrollY:"+scrollY);
                Log.i("main","---------oldScrollX:"+oldScrollX);
                Log.i("main","---------oldScrollY:"+oldScrollY);

                Log.i("tv1","---getY:"+tv1.getY() +"---getX:"+tv1.getX());
                Log.i("tv12","---getY:"+tv12.getY());
                Log.i("tv13","---getY:"+ tv13.getY());
                Log.i("tv14","---getY:"+ tv14.getY());
                Log.i("tv20","---getY:"+ tv20.getY());

                if(scrollY + getWindowManager().getDefaultDisplay().getHeight()>1800){
                    count++;
                }else {
                    count--;
                }
                Log.i("count","---count:"+ count);

            }
        });



    }
}
