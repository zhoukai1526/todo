package com.iwintrue.todoapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.iwintrue.commonlibrary.data.bean.RangeData;
import com.iwintrue.commonlibrary.data.io.ImageRequest;
import com.iwintrue.commonlibrary.data.io.Request;
import com.iwintrue.commonlibrary.data.io.download.DownloadInfo;
import com.iwintrue.commonlibrary.data.io.download.DownloadManager;
import com.iwintrue.commonlibrary.data.io.download.DownloadViewHolder;
import com.iwintrue.commonlibrary.utils.BitmapUtils;
import com.iwintrue.commonlibrary.utils.L;
import com.iwintrue.commonlibrary.utils.MyCountDownTime;
import com.iwintrue.commonlibrary.utils.SDCardUtils;
import com.iwintrue.mylibrary.utils.StringUtils;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpanAty extends AppCompatActivity {

    private TextView tv_text;
    private EditText ed_text;
    private ImageView iv_icon;
    private ImageView iv_icon2;
    private ImageView iv_icon3;
    private ImageView iv_icon4,iv_icon5;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_span_aty);
        tv_text = (TextView) findViewById(R.id.tv_text);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_icon2 = (ImageView) findViewById(R.id.iv_icon2);
        iv_icon3 = (ImageView) findViewById(R.id.iv_icon3);
        iv_icon4 = (ImageView) findViewById(R.id.iv_icon4);
        iv_icon5 = (ImageView) findViewById(R.id.iv_icon5);
        ed_text = (EditText) findViewById(R.id.ed_text);
        web = (WebView) findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDomStorageEnabled(true);

        tv_text.setHint("我是世上设计计算");
        ed_text.setHint("我是世上设计计算");

        StringUtils.setHintTextColor(tv_text,"#ff0000",0,6);
        StringUtils.setHintTextColor(ed_text,"#ff0000",0,6);

        Bitmap bitmap = BitmapUtils.toGrayscale(BitmapUtils.getBitmap(this,R.drawable.background));
        iv_icon.setImageBitmap(bitmap);

        web.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                L.i("-------------shouldOverrideUrlLoading--------------"+request.getRequestHeaders().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                L.i("-------------shouldOverrideUrlLoading----------222----");
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        web.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                L.i("------------------url--------"+url);
                L.i("---------------------userAgent-----"+userAgent);
                L.i("---------------------contentDisposition-----"+contentDisposition);
                L.i("----------------------mimetype----"+mimetype);
                L.i("----------------------contentLength----"+contentLength);
            }
        });
    }

    MyCountDownTime countDownTime = new MyCountDownTime(60000, 1000, new MyCountDownTime.CountDownTimerCallBack() {
        @Override
        public void onTickCallBack(long millisUntilFinished) {
            tv_text.setText(millisUntilFinished+"");
        }

        @Override
        public void onFinishCallBack() {
            tv_text.setText("60s");
        }
    });

    boolean isOpen = true;
    String url_gif = "http://img4.duitang.com/uploads/blog/201403/06/20140306090349_RJ2c3.thumb.700_0.gif";
    String url_jpg = "http://img5.imgtn.bdimg.com/it/u=2873303569,1224067546&fm=26&gp=0.jpg";
    public void click(View view) throws PackageManager.NameNotFoundException {

        loadPatch(view);

        tv_text.setText("更新之后的apk");

        countDownTime.start();

        Bitmap bitmap = BitmapUtils.rotateBitMap(BitmapUtils.getBitmap(this,R.drawable.background),90);
        iv_icon2.setImageBitmap(bitmap);
        Bitmap bitmap2 = BitmapUtils.rotateBitMap(BitmapUtils.getBitmap(this,R.drawable.background),180);
        iv_icon3.setImageBitmap(bitmap2);


        ImageRequest.bindImage(this,iv_icon4,url_gif);


        String downloadPath = "http://app-dw.crfchina.com/android/crf_app_last.apk";
        String uploadPath  = "http://wx.bianwoyou.com/ytx/upload/";

        View view1 = new View(this);
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.setUrl(downloadPath);

        try {
            DownloadManager.getInstance().startDownload(downloadInfo.getUrl(), "aaa", SDCardUtils.getSDCardPath()+"down.apk", true, false, new DownloadViewHolder(view1,downloadInfo) {

                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
//                    Intent intent = new Intent(SpanAty.this, DownloadActivity.class);
//                    startActivity(intent);
                }

                @Override
                public void onLoading(long total, long current) {
                    L.i("-----------------total------"+total+"   ----current:"+current);
                }

                @Override
                public void onSuccess(File result) {
                    L.i("-----------------result------"+result.getAbsolutePath());
                    L.i("-----------------length------"+result.length());

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(Callback.CancelledException cex) {

                }
            });
        } catch (DbException e) {
            e.printStackTrace();
        }


        Map<String,String>map = new HashMap<>();
        map.put("dId", "358099061578258");
        map.put("cId", "UCzPbpRF");

//        UploadRequest.uploadFile(uploadPath,map, new File(SDCardUtils.getSDCardPath() + "/ytx/test.jpg"), new UploadRequest.FileCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                if(com.iwintrue.commonlibrary.Constants.DEBUG){
//                    L.i(">>>>>>>>onSuccess>>>>>>>>"+result);
//                }
//            }
//        });

//        List<File> fileList = new ArrayList<>();
//        fileList.add(new File(SDCardUtils.getSDCardPath() + "/ytx/test.jpg"));
//        fileList.add(new File(SDCardUtils.getSDCardPath() + "/yxt/ic_launcher.png"));
//        UploadRequest.uploadFile(uploadPath,map,fileList , new UploadRequest.FileCallBack() {
//            @Override
//            public void onSuccess(String result) {
//                if(com.iwintrue.commonlibrary.Constants.DEBUG){
//                    L.i(">>>>>>>>onSuccess>>>>>>>>"+result);
//                }
//            }
//        });

//        L.i("-------ArrayList------"+ ObjectUtils.isEmpty(new ArrayList<String>()));
//        L.i(">>>>>>>>>>"+ ObjectUtils.isEmpty(new ArrayList<String>(9).size()));
//        ArrayList arrayList = new ArrayList();
//        arrayList.add("aaaa");
//        L.i(">>>>>>>>>>"+ ObjectUtils.isEmpty(arrayList));
//        HashMap<String,String> map = new HashMap<>();
//        map.put("aaa","bbbb");
//        L.i(">>>>>>>>>>"+ ObjectUtils.isEmpty(new HashMap<String,String>(9)));
//        L.i(">>>>>>>>>>"+ ObjectUtils.isEmpty(map));
//        L.i("-------1222------"+ ObjectUtils.isEmpty(1222));


//        new MyRequest(this).execute(new RequestCallManager<RangeData>(){
//
//            @Override
//            public void success(RangeData baseBean) {
//
//                L.i("----------distance--------"+baseBean.distance);
//                L.i("----------distance--------"+baseBean.distance);
//
//            }
//
//        });

//        ImageRequest.bindImage(this,iv_icon5,url_jpg);
//        ImageRequest.bindImage(this,iv_icon5, BitmapUtils.getBitmapByte(bitmap));
//        ImageRequest.bindImage(this,iv_icon5, url_jpg,R.drawable.logo);
//        L.i("-----getApplicationMeta---"+ AppUtils.getApplicationMeta(this,"zhouzhou"));
//        L.i("-----getActivityMeta---"+ AppUtils.getActivityMeta(this,"zhouzhou"));
//        L.i("-----getActivityMeta---"+ AppUtils.getActivityMeta(this,"bbb"));
//        L.i("-----getPhoneNumber---"+ TelePhoneUtil.getPhoneNumber(this));
//        L.i("-----getDid---"+ TelePhoneUtil.getDid(this));
//        L.i("-----getAllApp---"+ TelePhoneUtil.getAllApp(this));
//        L.i("-----PHONE_TYPE---"+ TelePhoneUtil.PHONE_TYPE);
//        L.i("-----SDK---"+ Build.VERSION.SDK);
//        String url = "https://www.baidu.com";
//        String url = "http://app-dw.crfchina.com/android/crf_app_last.apk";
//        Uri uri = Uri.parse(url);
//        L.i("-------getQuery-------"+uri.getQuery());
//        L.i("-------getPath-------"+uri.getPath());
//        L.i("-------getHost-------"+uri.getHost());
//        L.i("-------getAuthority-------"+uri.getAuthority());
//        L.i("-------getEncodedAuthority-------"+uri.getEncodedAuthority());
//        L.i("-------getEncodedPath-------"+uri.getEncodedPath());
//        L.i("-------getScheme-------"+uri.getScheme());
//        L.i("-------getPathSegments-------"+uri.getPathSegments());
//        L.i("-------getEncodedUserInfo-------"+uri.getEncodedUserInfo());
//        web.loadUrl(url);




//        File file = BitmapUtils.getScaleRoteBitmapFile(this,Environment.getExternalStorageDirectory().getAbsolutePath()+"/photo.jpg",200,200,0);
//        L.i("------------getDataDirectory---------"+Environment.getDataDirectory().getAbsolutePath());
//        L.i("----------------getDownloadCacheDirectory-----"+Environment.getDownloadCacheDirectory().getAbsolutePath());
//        L.i("-----------------getExternalStoragePublicDirectory----"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
//        L.i("-------------------getRootDirectory--"+Environment.getRootDirectory().getAbsolutePath());
//        L.i("-------------------getRootDirectory--"+file.getAbsolutePath());
//        L.i("-------------------getExternalFilesDir--"+this.getExternalFilesDir(null).getAbsolutePath());
//        L.i("-------------------getExternalCacheDir--"+this.getExternalCacheDir().getAbsolutePath());


//        L.i("----click------");
//        L.i("----getRootDirectoryPath---"+ SDCardUtils.getRootDirectoryPath());
//        L.i("----getSDCardPath---"+ SDCardUtils.getSDCardPath());
//        L.i("----getFreeBytes---"+ SDCardUtils.getFreeBytes(SDCardUtils.getSDCardPath()));
//        L.i("----getSDCardAllSize---"+ SDCardUtils.getSDCardAllSize());

//        L.i("----click------");
//        L.i("----isWifi---"+ NetUtils.isWifi(this));
//        L.i("----isConnect---"+ NetUtils.isConnected(this));
//        L.isDebug = false;
//        L.i("----click-2-----");
//        L.i("----isWifi-2--"+ NetUtils.isWifi(this));
//        L.i("----isConnect-2--"+ NetUtils.isConnected(this));

//        if(isOpen){
//            isOpen = false;
//            KeyBoardUtils.closeKeybord(ed_text,this);
//        }else {
//            isOpen = true;
//            KeyBoardUtils.openKeybord(ed_text,this);
//        }

//        Log.i("main",""+AppUtils.getAppName(SpanAty.this));
//        Log.i("main",""+AppUtils.getPackageName(SpanAty.this));
//        Log.i("main",""+AppUtils.getDid(SpanAty.this));
//        Log.i("main",""+AppUtils.getVersionName(SpanAty.this));
//        Log.i("main",""+AppUtils.getVersionCode(SpanAty.this));

//        Log.i("main",""+ DensityUtils.dp2px(this,100));
//        Log.i("main",""+  DensityUtils.sp2px(this,100));
//        Log.i("main",""+  DensityUtils.px2dp(this,100));
//        Log.i("main",""+  DensityUtils.px2sp(this,100));

//        Log.i("main",""+ FileUtil.getFolderPath(this,FileUtil.TYPE_IMAGE,"todo"));
//        String filePath = FileUtil.getFolderPath(this,5,"todo")+"test.txt";
//        String filePath = "/storage/emulated/0/Android/data/com.iwintrue.todo/files/test.txt";
//        boolean isNewFile = FileUtil.createNewFile("/storage/emulated/0/Android/data/com.iwintrue.todo/files/test.txt");
//        Log.i("main",""+ isNewFile);
//        Log.i("main",""+ FileUtil.createFolder("/storage/emulated/0/Android/data/com.iwintrue.todo/files/Files"));

//        if(isNewFile){
//            try {
//                FileUtil.writeToFile(filePath,"abcdefghijklmn");
//                boolean deleteFile = FileUtil.deleteFile(filePath);
//                Log.i("main","-----deleteFile:"+deleteFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.i("main",""+  FileUtil.createFileName(".jpg"));

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
            RequestParams params = new RequestParams("http://wx.bianwoyou.com/ytx/toilet/range/map/");
            params.addBodyParameter("pushId","1098308151411987164_4057958840791172561");
            params.addBodyParameter("deviceType","android");
            params.addBodyParameter("curLng","116.346602");
            params.addBodyParameter("latAngle","39.975959");
            params.addBodyParameter("lngCenter","116.346602");
            params.addBodyParameter("lngAngle","116.343085");
            params.addBodyParameter("curLat","39.980221");
            params.addBodyParameter("version","2.2.3");
            params.addBodyParameter("dId","358099061578258");
            params.addBodyParameter("latCenter","39.98022");
            params.addBodyParameter("cId","UCzPbpRF");
            return params;
        }

        @Override
        public  RangeData parse(String json) {
            L.i("------json"+json);
            RangeData range = new RangeData();
            com.alibaba.fastjson.JSONObject object= com.alibaba.fastjson.JSONObject.parseObject(json);
            range = JSONObject.parseObject(object.getJSONObject("116.342937,39.975538").toString(),RangeData.class);
            return range;
        }
    }

    public void loadPatch(View view) {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed.apk");
    }



}
