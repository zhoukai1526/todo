package com.iwintrue.todoapplication;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.iwintrue.commonlibrary.data.io.ImageRequest;
import com.iwintrue.commonlibrary.utils.AppUtils;
import com.iwintrue.commonlibrary.utils.BitmapUtils;

import org.xutils.x;

/**
 * Created by zhoukai on 2017/6/7.
 */

public class App extends Application {

    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        //设置bitmap缓存
        BitmapUtils.AlbumName = "todo";
        //设置图片缓存
        ImageRequest.isCache = true;
        // 初始化patch管理类
        mPatchManager = new PatchManager(this);
        // 初始化patch版本
        mPatchManager.init(AppUtils.getVersionName(this));
        mPatchManager.loadPatch();


    }
}
