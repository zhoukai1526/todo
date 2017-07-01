package com.iwintrue.todoapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iwintrue.commonlibrary.utils.T;

import java.io.IOException;

import static com.iwintrue.todoapplication.App.mPatchManager;

public class PatchAty extends AppCompatActivity  implements View.OnClickListener{

    private TextView tv_text;
    private Button btn_calculate ,btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patch_aty);

        initView();
    }

    private void initView() {
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_calculate = (Button) findViewById(R.id.btn_caculate);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_calculate.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_caculate:
                tv_text.setText("加补丁之后的版本"+CalUtils.getResult()+"");
                break;
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void update() {
        String patchFileStr = Environment.getExternalStorageDirectory().getAbsolutePath() +"/fix.apatch" ;
        try {
            mPatchManager.addPatch(patchFileStr);
            T.showShort(this,"修复成功！！！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
