package com.iwintrue.todoapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.iwintrue.commonlibrary.utils.T;

/**
 * Created by zhoukai on 2017/6/20.
 */

public class MyServise extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);
        T.showLong(this,"服务启动了。。。。。。。。");
        Intent ootStartIntent=new Intent(this,SpanAty.class);

        ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        this.startActivity(ootStartIntent);
    }
}
