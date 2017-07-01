package com.iwintrue.todoapplication.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.iwintrue.commonlibrary.data.bean.BaseBean;
import com.iwintrue.todoapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MulTypeAty extends AppCompatActivity {

    private List<BaseBean> list_base = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_aty);
        initData();
        initView();
    }

    private void initView() {

        ListView lv = (ListView) findViewById(R.id.lv);
        myAdapter = new MyAdapter(this,list_base, R.layout.lv_item);

        MyMulAdapter mulAdapter = new MyMulAdapter(list_base,this);
        lv.setAdapter(mulAdapter);

    }

    public  void initData(){

        for(int i=0;i<10;i++){

            BaseBean entity = new BaseBean();
            entity.code = i+"";
            entity.msg = "这是msg"+i;
            list_base.add(entity);
        }
    }

    public void click(View view) {
        initData();
    }

    public class MyMulAdapter extends MulTypeAdapter<BaseBean>{

        public MyMulAdapter(List<BaseBean> datas, Context context) {
            super(datas, context);
            addItem(new Item(R.layout.item1));
            addItem(new Item(R.layout.item2));
        }
    }
}
