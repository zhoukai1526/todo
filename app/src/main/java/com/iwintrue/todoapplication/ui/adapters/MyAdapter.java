package com.iwintrue.todoapplication.ui.adapters;

import android.content.Context;

import com.iwintrue.todoapplication.R;
import com.iwintrue.todoapplication.io.BaseEntity;

import java.util.List;

/**
 * Created by zhoukai on 2017/5/4.
 */

public class MyAdapter extends CommonBaseAdapter<BaseEntity> {

    public MyAdapter(Context context, List<BaseEntity> list,int layoutId) {
        super(context, list,layoutId);
    }


    @Override
    public void setData(CommonViewHolder viewHolder,  List<BaseEntity> list,int position) {

        viewHolder.setText(R.id.tv_code,list.get(position).code+"");
        viewHolder.setText(R.id.tv_code,list.get(position).msg);

        viewHolder.bindImage(R.id.iv_icon,R.mipmap.ic_launcher);
        if(position ==5){
            viewHolder.bindImage(R.id.iv_icon,"https://b-ssl.duitang.com/uploads/item/201208/08/20120808114607_yhVUJ.thumb.700_0.jpeg",
                    R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        }

        if(position ==6){
            viewHolder.bindImage(R.id.iv_icon,"https://b-ssl.duitang.com/uploads/item/201208/08/20120808114607_yhVUJ.thumb.700_0.jpeg",
                    R.mipmap.ic_launcher,R.mipmap.ic_launcher,true);

        }

    }


}
