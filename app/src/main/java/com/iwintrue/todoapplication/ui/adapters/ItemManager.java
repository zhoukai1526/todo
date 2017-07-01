package com.iwintrue.todoapplication.ui.adapters;

import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoukai on 2017/5/4.
 */

public class ItemManager {

    //高效的map 节省50%缓存，但是key Integer
    SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
    List<Integer> positions = new ArrayList<>();


    public  int getItemCount(){
        return sparseArrayCompat.size();
    }



    public  void addItemLayout(Item item){

        sparseArrayCompat.put(getItemCount(),item);
    }

    public Item  getItemLayout(int position){

        Item item = null;
        if(positions.contains(position)){
             item = (Item) sparseArrayCompat.get(position);
        }


        return item;
    }

}
