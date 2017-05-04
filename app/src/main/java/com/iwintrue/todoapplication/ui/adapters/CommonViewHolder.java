package com.iwintrue.todoapplication.ui.adapters;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iwintrue.todoapplication.ui.adapters.glide.GlideCircleTransform;

/**
 * Created by zhoukai on 2017/5/4.
 */

public class CommonViewHolder  {

    private View convertView;
    private SparseArray<View> views;
    private Context context;
    private final GlideCircleTransform transform;

    public CommonViewHolder(View convertView) {

        this.convertView = convertView;
        views = new SparseArray<>();
        context = convertView.getContext();
        transform = new GlideCircleTransform(context);
    }


    public <T extends View>T getViewByID(int id){

        View view = views.get(id);
        if(view == null){
            view = convertView.findViewById(id);
            views.put(id,view);
        }
        return (T)view;
    }

    public  CommonViewHolder  setText(int id,String str){

        ((TextView)getViewByID(id)).setText(str);

        return this;
    }

    /**
     * 加载本地图片，使用glide提高效率
     * @param id
     * @param drawable
     * @return
     */
    public CommonViewHolder  bindImage(int id,int drawable){

        ImageView imageView = getViewByID(id);
        Glide.with(context).load(drawable).into(imageView);
        return this;
    }

    /**
     * 加载网络图片
     * @param id
     * @param url
     * @param failDrawable
     * @return
     */
    public CommonViewHolder  bindImage(int id,String url,int failDrawable){

        ImageView imageView = getViewByID(id);
        Glide.with(context).load(url).error(failDrawable).
                placeholder(failDrawable).into(imageView);

        return this;

    }

    /**
     *
     * @param id
     * @param url
     * @param failDrawable
     * @param placeHolder
     * @return
     */

    public CommonViewHolder  bindImage(int id,String url,int failDrawable,int placeHolder){

        ImageView imageView = getViewByID(id);
        Glide.with(context).load(url).error(failDrawable).
                placeholder(placeHolder).into(imageView);

        return this;

    }

    public CommonViewHolder bindImage(int id,String url,int failDrawable,int placeHolder,boolean isCircle){

        ImageView imageView = getViewByID(id);
        if(isCircle)
            Glide.with(context).load(url).placeholder(placeHolder).error(failDrawable).
                    transform(transform).into(imageView);
        else
            Glide.with(context).load(url).error(failDrawable).
                    placeholder(placeHolder).into(imageView);


        return this;
    }
}
