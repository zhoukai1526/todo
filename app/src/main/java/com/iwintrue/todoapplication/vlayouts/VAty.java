package com.iwintrue.todoapplication.vlayouts;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.iwintrue.todoapplication.R;

import java.util.LinkedList;
import java.util.List;

public class VAty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaty);
        initView();
    }

    private void initView() {

        //配置layoutManager
        final RecyclerView rv_view = (RecyclerView) findViewById(R.id.rv_view);
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        rv_view.setLayoutManager(layoutManager);

        //设置回收池大小
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rv_view.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        rv_view.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });




        final List<LayoutHelper> helpers = new LinkedList<>();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setItemCount(25);
//        gridLayoutHelper.bindLayoutView(getLayoutInflater().inflate(R.layout.activity_delete_photo_aty,rv_view,false));

        OnePlusNLayoutHelper onePlus = new OnePlusNLayoutHelper(3);

        final ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT, 100, 100);

        helpers.add(DefaultLayoutHelper.newHelper(7));
        helpers.add(scrollFixLayoutHelper);
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);
        helpers.add(onePlus);

        layoutManager.setLayoutHelpers(helpers);


        rv_view.setAdapter(
                new VirtualLayoutAdapter(layoutManager) {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MainViewHolder(new TextView(VAty.this));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT, 300);
                        holder.itemView.setLayoutParams(layoutParams);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(rv_view.getContext(),"您点击了"+position,Toast.LENGTH_SHORT).show();
                     }
                });

//                        ((TextView) holder.itemView).setText(Integer.toString(position));

                        if (position == 7) {
                            layoutParams.height = 160;
                            layoutParams.width = 160;
                        } else if (position > 35) {
                            layoutParams.height = 200 + (position - 30) * 100;
                        }

                        if (position > 35) {
                            holder.itemView.setBackgroundColor(0x66cc0000 + (position - 30) * 128);
                        } else if (position % 2 == 0) {
                            holder.itemView.setBackgroundColor(0xaa00ff00);
                        } else {
                            holder.itemView.setBackgroundColor(0xccff00ff);
                        }
                    }

                    @Override
                    public int getItemCount() {
                        List<LayoutHelper> helpers = getLayoutHelpers();
                        if (helpers == null) {
                            return 0;
                        }
                        int count = 0;
                        for (int i = 0, size = helpers.size(); i < size; i++) {
                            count += helpers.get(i).getItemCount();
                        }
                        return count;
                    }
                });

    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }

}
