package com.iwintrue.todoapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iwintrue.commonlibrary.data.io.Request;

import org.xutils.http.RequestParams;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void click(View view) {
        Toast.makeText(this,"请求数据",Toast.LENGTH_SHORT).show();
//        new MyRequest(this).execute(true, new Request.RequestCallback<Request.Result>() {
//
//            @Override
//            public void onSuccess(Request.Result result) {
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(Callback.CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });

    }

    class MyRequest extends Request{

        public MyRequest(Context context) {
            super(context);
        }

        @Override
        public String getName() {
            return getClass().getSimpleName();
        }

        @Override
        public RequestParams params() {
            RequestParams params = new RequestParams("http://192.168.16.33:8080");
            params.addBodyParameter("name","周凯");
            params.addBodyParameter("age",14+"");
            return params;
        }

        @Override
        public <T> T parse(String json) {
            Log.i("main","json:"+json);

            return null;
        }
    }

}
