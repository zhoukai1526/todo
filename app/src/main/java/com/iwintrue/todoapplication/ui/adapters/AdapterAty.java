package com.iwintrue.todoapplication.ui.adapters;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iwintrue.todoapplication.BaseAty;
import com.iwintrue.todoapplication.R;
import com.iwintrue.todoapplication.io.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class AdapterAty extends BaseAty {

    private List<BaseEntity>  list_base = new ArrayList<>();
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
        lv.setAdapter(myAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdapterAty.this,"您点击了"+id,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  void initData(){

        for(int i=0;i<10;i++){

            BaseEntity entity = new BaseEntity();
            entity.code = i;
            entity.msg = "这是msg"+i;
            list_base.add(entity);
        }
    }

    public void click(View view) {
        initData();
        myAdapter.setList(list_base);
    }
}

