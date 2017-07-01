package com.iwintrue.todoapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.iwintrue.commonlibrary.utils.FileUtil;
import com.iwintrue.commonlibrary.utils.L;
import com.iwintrue.commonlibrary.utils.T;

import java.io.IOException;

/**
 * Created by zhoukai on 2017/6/20.
 */


public class BootBroadcastReceiver extends BroadcastReceiver {

    static final String action_boot="android.intent.action.BOOT_COMPLETED";



    @Override

    public void onReceive(Context context, Intent intent) {

        T.showLong(context,"开机啦。。。。。。。。");
        L.i("--------onReceive---onReceive---------"+intent.getAction());
        String filePath = "/storage/emulated/0/Android/data/com.iwintrue.todo/files/test.txt";
        try {
            FileUtil.writeToFile(filePath,"开机啦lll2222");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent_service = new Intent(context,MyServise.class);
        context.startService(intent_service);




    }



}