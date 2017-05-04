package com.iwintrue.todoapplication.ui.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhoukai on 2017/5/4.
 */

public abstract class CommonBaseAdapter<T> extends BaseAdapter {


    private List<T> list;  //数据集合
    private  Context context ; //上下文
    private  int layoutId;      //布局文件

    private  LayoutInflater inflate;

    public CommonBaseAdapter(Context context ,List<T> list,int layoutId) {

        this.list = list;
        this.context = context;
        this.layoutId = layoutId;
        inflate = LayoutInflater.from(context);
    }

    public CommonBaseAdapter(Context context ,T[] list,int layoutId) {

        this.list = Arrays.asList(list);
        this.context = context;
        this.layoutId = layoutId;
        inflate = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (list != null&&list.size()>0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommonViewHolder viewHolder = null;
        if(convertView==null){
            convertView = inflate.inflate(layoutId,parent,false);
            viewHolder = new CommonViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (CommonViewHolder) convertView.getTag();
        }

        //设置数据
        setData(viewHolder,list,position);
        return convertView;
    }

    public abstract void setData(CommonViewHolder viewHolder,List<T>list,int position);

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
}
