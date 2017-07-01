package com.iwintrue.todoapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by zhoukai on 2017/5/4.
 */

public abstract class MulTypeAdapter<T> extends BaseAdapter {

    private List<T> datas;
    private Context context;
    ItemManager itemManager = new ItemManager();

    public MulTypeAdapter(List<T> datas, Context context) {
        this.datas = datas;
        this.context = context;

    }

    public void  addItem(Item item){
        itemManager.addItemLayout(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Item item = null;
        if(position%2==0){
            item = itemManager.getItemLayout(0);
        }else {
            item = itemManager.getItemLayout(1);
        }

        int layoutId = item.getLayoutId();
        CommonViewHolder viewHolder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(layoutId,parent,false);
            viewHolder  = new CommonViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CommonViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        //每一个position对应一个
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {

        if(itemManager.getItemCount()>0){
            return itemManager.getItemCount();
        }

        return super.getViewTypeCount();
    }



    @Override
    public int getCount() {

        if (datas != null&&datas.size()>0)
            return datas.size();

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
