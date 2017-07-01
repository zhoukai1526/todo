package com.iwintrue.todoapplication.ui.adapters;

import java.util.List;

/**
 * Created by zhoukai on 2017/5/4.
 */

public class Item {


    private int layoutId;
    private List<Integer> positions;

    public Item(int layoutId){
        this.layoutId = layoutId;
    }
    public Item(int layoutId,List<Integer> positions){
        this.layoutId = layoutId;
        this.positions = positions;
    }


    public List<Integer> getPositions() {
        return positions;
    }

    public  int  getLayoutId(){


        return layoutId;
    }
}
