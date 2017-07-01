package com.iwintrue.todoapplication.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.iwintrue.commonlibrary.utils.T;
import com.iwintrue.todoapplication.R;

@Content(R.layout.activity_annotation_aty2)
public class AnnotationAty2 extends AppCompatActivity {

    @Inject(R.id.tv_annotataion)
    public TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.injectContent(this);
        ViewUtils.injectView2(this);
        ViewUtils.injectEvents(this);
        setTitle("annation11111");
        tv.setText("自定义注解");
    }

    @OnClick(R.id.tv_annotataion)
    public  void click(View view){
        ((TextView)view).setText("annation222");
        T.showShort(this,"点击了按钮");
//        getExternalFilesDir()
//        getExternalFilesDir()
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/people.txt";
//        File file = new File(path);
//        try {
//            boolean newFile = file.createNewFile();
//            L.i("--------newFile---"+newFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
