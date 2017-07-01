package com.iwintrue.todoapplication.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwintrue.commonlibrary.ui.AutoPopuWindow;
import com.iwintrue.commonlibrary.utils.ScreenUtils;
import com.iwintrue.commonlibrary.utils.T;
import com.iwintrue.todoapplication.R;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import org.xutils.common.util.DensityUtil;

import static com.iwintrue.todoapplication.R.id.rl;

public class AnnotationAty extends Activity {

    private AutoPopuWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_aty);
//        ViewUtils.injectContentView(this);
        ViewUtils.injectContent(this);
        ViewUtils.injectViews(this);
        ViewUtils.injectEvents(this);
    }


    public void share(View view){
        showPopupWindow(view);
    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
        contentView.findViewById(rl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.tv_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(AnnotationAty.this,"二维码");
            }
        });
        contentView.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(AnnotationAty.this,"分享");
            }
        });
        contentView.findViewById(R.id.tv_reward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T.showShort(AnnotationAty.this,"捐款");
            }
        });
        int stateHeight = ScreenUtils.getStatusHeight(this);
        int navigation = DensityUtil.dip2px(48);
        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScreenHeight(this);
        popupWindow = new AutoPopuWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }




    private  float alpha = 0;
//    @OnClick({R.id.btn,R.id.btn2})
    public  void click(View view) {


        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
        T.showShort(this, "修复了这个类点击按钮" + view.getTag());
        alpha = alpha+0.1f;


//        String downloadPath = "http://app-dw.crfchina.com/android/crf_app_last.apk";
//        String uploadPath = "http://wx.bianwoyou.com/ytx/upload/";

//        View view1 = new View(this);
//        DownloadInfo downloadInfo = new DownloadInfo();
//        downloadInfo.setUrl(downloadPath);

//        try {
//            DownloadManager.getInstance().startDownload(downloadInfo.getUrl(), "aaa", SDCardUtils.getSDCardPath() + "down.apk", true, false, new DownloadViewHolder(view1, downloadInfo) {
//
//                @Override
//                public void onWaiting() {
//
//                }
//
//                @Override
//                public void onStarted() {
////                    Intent intent = new Intent(AnnotationAty.this, DownloadActivity.class);
////                    startActivity(intent);
//                }
//
//                @Override
//                public void onLoading(long total, long current) {
//                    L.i("-----------------total------" + total + "   ----current:" + current);
//                }
//
//                @Override
//                public void onSuccess(File result) {
//                    L.i("-----------------result------" + result.getAbsolutePath());
//                    L.i("-----------------length------" + result.length());
//
//                }
//
//                @Override
//                public void onError(Throwable ex, boolean isOnCallback) {
//                    L.i("---------------------ex:"+ex);
//                    L.i("---------------------isOnCallback:"+isOnCallback);
//                }
//
//                @Override
//                public void onCancelled(Callback.CancelledException cex) {
//                    L.i("---------------------onCancelled:"+cex);
//
//                }
//            });
//        } catch (DbException e) {
//            e.printStackTrace();
//        }

    }


}
