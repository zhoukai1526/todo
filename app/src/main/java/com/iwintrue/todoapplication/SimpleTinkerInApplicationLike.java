package com.iwintrue.todoapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alipay.euler.andfix.patch.PatchManager;
import com.iwintrue.commonlibrary.data.io.ImageRequest;
import com.iwintrue.commonlibrary.utils.AppUtils;
import com.iwintrue.commonlibrary.utils.BitmapUtils;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import org.xutils.x;

import static com.iwintrue.todoapplication.App.mPatchManager;

/**
 * Created by zhoukai on 2017/6/27.
 */

@DefaultLifeCycle(application = ".SimpleTinkerInApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class SimpleTinkerInApplicationLike extends ApplicationLike {
    public SimpleTinkerInApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }
    @Override
    public void onBaseContextAttached(Context base) {

        super.onBaseContextAttached(base);
        TinkerManager.setTinkerApplicationLike(this);
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);
        TinkerManager.installTinker(this);

    }

    /**
     * 绑定了application的生命周期，
     * 在application中的操作移植到此方法
     */
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(getApplication());
        x.Ext.setDebug(BuildConfig.DEBUG);
        //设置bitmap缓存
        BitmapUtils.AlbumName = "todo";
        //设置图片缓存
        ImageRequest.isCache = true;
        // 初始化patch管理类
        mPatchManager = new PatchManager(getApplication());
        // 初始化patch版本
        mPatchManager.init(AppUtils.getVersionName(getApplication()));
        mPatchManager.loadPatch();
    }

}
