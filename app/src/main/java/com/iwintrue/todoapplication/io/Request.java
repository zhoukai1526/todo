package com.iwintrue.todoapplication.io;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.iwintrue.todoapplication.Constants;
import com.iwintrue.todoapplication.R;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * @author dean
 * @date 2016年4月26日
 */
public abstract class Request {

	public static int requestCount;
	 public static final String SERVER = "https://wwwculture.twsm.com.cn";//外网


	public static final long EXPIRE_SECOND = 1000;
	public static final long EXPIRE_SECOND_10 = 1000 * 10;
	public static final long EXPIRE_MINUTE = 1000 * 60;
	public static final long EXPIRE_MINUTE_10 = 1000 * 60 * 10;
	public static final long EXPIRE_HOUR = 1000 * 60 * 60;
	public static final long EXPIRE_DAY = 1000 * 60 * 60 * 24;
	public static final long EXPIRE_WEEK = 1000 * 60 * 60 * 24 * 7;
	public static final long EXPIRE_MONTH = 1000 * 60 * 60 * 24 * 30;
	public static final long EXPIRE_YEAR = 1000 * 60 * 60 * 24 * 365;

	public Context context;

	// /** Https 证书验证对象 */
	// private static SSLContext s_sSLContext = null;

	// 等待框
	static ProgressDialog mDialog;

	public Request(Context context) {

		this.context = context;

		// if(context!=null && context instanceof BaseActivity)
		// bact = (BaseActivity) context;

	}

	public abstract String getName();

	public abstract RequestParams params();

	public abstract <T> T parse(String json);

	public interface RequestCallback<T> {

		// public boolean onCache(T result);

		public void onSuccess(T t);

		public void onError(Throwable ex, boolean isOnCallback);

		public void onCancelled(Callback.CancelledException cex);

		public void onFinished();

	}

	// 是否使用缓存
	private boolean isCache;

	public Request setCache(boolean isCache) {

		this.isCache = isCache;

		return this;

	}

	public boolean isCache() {

		return isCache;
	}

	// 是否展示服务器提示
	private boolean isShowServerMsg = true;

	public Request setShowServerMsg(boolean isShowServerMsg) {

		this.isShowServerMsg = isShowServerMsg;

		return this;
	}

	public boolean isShowServerMsg() {

		return isShowServerMsg;
	}

	RequestCallback mCallback;

	/**
	 * 强制刷新
	 */
	public void forceRefresh() {

		if (mCallback == null) {
			// throw new NullPointerException("Request mCallback is null");
			LogUtil.e("Request mCallback is null");

			return;
		}

		isCache = false;

		execute(mCallback);

	}

	static boolean needSign = true;

	public <T> Callback.Cancelable execute(boolean needSign, boolean showDialog, final RequestCallback<T> callback) {
		this.needSign = needSign;
		return execute(showDialog, callback);
	}

	public <T> Callback.Cancelable execute(final RequestCallback<T> callback) {

		return execute(true, callback);
	}

	RequestCancelable requestCancelable;

	public <T> Callback.Cancelable execute(boolean showDialog, final RequestCallback<T> callback) {

		// isCache = !isShowServerMsg || isCache;

		if (showDialog)
			showLoadingDialog();

		requestCount++;

		// params.setCacheMaxAge();

		if (Constants.DEBUG) {
			for (KeyValue kv : params().getQueryStringParams()) {
				LogUtil.d(kv.key + " >>> " + kv.getValueStr());
			}
			for (KeyValue kv : params().getBodyParams()) {
				LogUtil.d(kv.key + " >>> " + kv.getValueStr());
			}
		}
		RequestParams params = params();

		// 测试用
		params.setConnectTimeout(1000 *20);
		bindParamsHeader(params);

		/** 判断https证书是否成功验证 */
		// SSLContext sslContext = getSSLContext(context);
		// if(null == sslContext){
		// LogUtil.d("Error:Can't Get SSLContext!");
		// }else {
		// params.setSslSocketFactory(sslContext.getSocketFactory());
		// //绑定SSL证书(https请求)
		// }

LogUtil.v(">>>>>>>> Request params : " + params.getQueryStringParams().toString());
		Callback.Cancelable cancelable = x.http().post(params, new Callback.CacheCallback<String>() {

			@Override
			public void onSuccess(String result) {

				if (result != null) {

					LogUtil.d(getName() + ": 网络请求任务成功");

					LogUtil.d(getName() + ": " + result);

					new ParseTask<T>(result, callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

				}
			}

			@Override
			public void onError(Throwable ex, boolean isOnCallback) {

				LogUtil.d(getName() + ": 网络请求任务失败");

				ex.printStackTrace();

				if (context != null) {

					if (ex instanceof ConnectException) {

						Toast.makeText(context, R.string.app_error_net, Toast.LENGTH_SHORT).show();

					} else if (ex instanceof SocketTimeoutException) {

						Toast.makeText(context, R.string.app_error_net, Toast.LENGTH_SHORT).show();
					}

				}

				callback.onError(ex, isOnCallback);

				 dismissLoadingDialog();
			}

			@Override
			public void onCancelled(CancelledException cex) {

				LogUtil.d(getName() + ": 网络请求任务被取消");

				callback.onCancelled(cex);

				 dismissLoadingDialog();
			}

			@Override
			public void onFinished() {

				LogUtil.d(getName() + ": 网络请求任务完成");

				callback.onFinished();

				requestCount--;
				needSign = true;
//				if (context instanceof BaseActivity)
//					((BaseActivity) context).removeRequestCancelable(requestCancelable);

				dismissLoadingDialog();
			}

			@Override
			public boolean onCache(String result) {

				if (isCache) {

					LogUtil.d(getName() + ": 使用缓存数据");

					new ParseTask<T>(result, callback).execute();
				}

				return isCache;
			}
		});

//		if (context instanceof BaseActivity) {

			requestCancelable = new RequestCancelable(this, cancelable);

//			((BaseActivity) context).addRequestCancelable(requestCancelable);

//		}

		return cancelable;
	}

	private static String getSign(List<KeyValue> params) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		StringBuffer str = new StringBuffer();
		String key;

		for (KeyValue kv : params) {
			map.put(kv.key, (String) kv.value);
		}
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			key = (String) it.next();
			if (map.get(key) != null && !TextUtils.isEmpty(map.get(key))) {
				str.append(key + "=" + map.get(key));
				str.append("&");
			}
		}
//		str.append("secretKey=" + Constants.secretKey);
		return MD5.md5(str.toString());
	}

	public static class Result extends BaseEntity {

		public String code;

		public String msg;

	}

	class ParseTask<T> extends AsyncTask<Void, Void, Result> {

		String json;

		RequestCallback<T> callback;

		T t;

		public ParseTask(String json, RequestCallback<T> callback) {

			this.json = json;

			this.callback = callback;
		}

		@Override
		protected Result doInBackground(Void... params) {
			if (callback == null) {

				Result mResult = new Result();

				mResult.code = "-1";

				mResult.msg = "RequestCallback is null";

				return mResult;

			}
			Result mResult;
			try {

				mResult = JSON.parseObject(json, Result.class);

				if (mResult != null)

					if (ResultCode.CODE_SUCCESS.equals(mResult.code)) {

						t = parse(json);

					}
			} catch (Exception e) {

				e.printStackTrace();

				return null;
			}
			return mResult;
		}

		@Override
		protected void onPostExecute(Result result) {

			super.onPostExecute(result);

			if (result == null && isShowServerMsg) {

				Toast.makeText(context, R.string.result_is_null, Toast.LENGTH_SHORT).show();

			} else {

				LogUtil.d(getName() + "  code:" + result.code + "  msg:" + result.msg);

				if (ResultCode.CODE_SUCCESS.equals(result.code)) {

					// if (t != null) {

					callback.onSuccess(t);
					// }
				} else if (ResultCode.CODE_SERVICE_ERROR.equals(result.code)) {

					callback.onError(null, false);

					if (isShowServerMsg)
						// Toast.makeText(context, R.string.app_error_net,
						// Toast.LENGTH_LONG).show();
						Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();

				} else {

					callback.onError(new Throwable(result.code), false);

					if (isShowServerMsg)
						Toast.makeText(context, result.msg, Toast.LENGTH_LONG).show();

				}
			}

			callback.onFinished();

			dismissLoadingDialog();

		}

	}

	private void showLoadingDialog() {

		 if (mDialog==null)
//		if (requestCount == 0 && context instanceof BaseActivity && !((BaseActivity) context).isFinishing()) {
			mDialog = ProgressDialog.show(context, "", context.getResources().getString(R.string.loading));
//		}

	}

	private void dismissLoadingDialog() {

		if (requestCount == 0 && mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}


	public static RequestParams bindParamsHeader(RequestParams params) {

//		String tokenCode = (null == UserManager.login) ? "" : UserManager.login.tokenCode;
//		// String tokenCode = Constants.tokenCode;
////		LogUtil.d(">>>>>>>>>>>>> request head tokenCode : " + tokenCode);
//		mStatistics = new Statistics();
//		mStatistics.sysAgent = Constants.SYS_AGENT;
//		mStatistics.sysAppId = Constants.SYS_APP_ID;
//		mStatistics.sysAppMarket = Constants.SYS_APP_MARKET;
//		mStatistics.sysCookieId = Constants.SYS_COOKIE_ID;
//		mStatistics.sysDevid = Constants.SYS_DEV_ID;
//		mStatistics.sysDevType = Constants.SYS_DEV_TYPE;
//		mStatistics.sysDpi = Constants.SYS_DPI;
//		// mStatistics.sysIP = Constants.SYS_IP;
//		mStatistics.sysNet = Constants.SYS_NET;
//		mStatistics.sysOs = Constants.SYS_OS;
//		// mStatistics.sysUid = Constants.SYS_UID;
//		mStatistics.sysVersion = Constants.SYS_VERSION;
//		if (Constants.STATISTIC_FLAG) {
//			params.setHeader("logParams", JSON.toJSONString(mStatistics));
//			LogUtil.v(">>>>> logParams : " + JSON.toJSONString(mStatistics));
//		}
//		params.setHeader("deviceId", Constants.deviceId);

//		params.setHeader("deviceType", "android");
//
//		// params.setHeader("tokenCode", "tokenCode="+tokenCode);
//		params.setHeader("cookie", "tokenCode=" + tokenCode);
//
//		String sign = getSign(params.getQueryStringParams());
//
//		if (needSign) {
//			params.addBodyParameter("appId", Constants.appId);
//			params.addBodyParameter("tokenCode", tokenCode);
//			params.addBodyParameter("sign", sign);
//		}

		return params;

	}

	/**
	 * 获取Https的证书
	 * 
	 * @param context
	 *            Activity（fragment）的上下文
	 * @return SSL的上下文对象
	 */
	public static SSLContext getSSLContext(Context context) {

		SSLContext s_sSLContext;

		CertificateFactory certificateFactory = null;
		InputStream inputStream = null;
		Certificate cer = null;
		KeyStore keystore = null;
		TrustManagerFactory trustManagerFactory = null;
		try {

			certificateFactory = CertificateFactory.getInstance("X.509");
			inputStream = context.getAssets().open("twsm.com.cn.crt");// 这里导入SSL证书文件

			try {
				// 读取证书
				cer = certificateFactory.generateCertificate(inputStream);
				LogUtil.d(cer.getPublicKey().toString());

			} finally {
				inputStream.close();
			}

			// 创建一个证书库，并将证书导入证书库
			keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			keystore.load(null, null); // 双向验证时使用
			keystore.setCertificateEntry("trust", cer);

			// 实例化信任库
			trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			// 初始化信任库
			trustManagerFactory.init(keystore);

			s_sSLContext = SSLContext.getInstance("TLS");
			s_sSLContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

			// 信任所有证书 （官方不推荐使用）
			// s_sSLContext.init(null, new TrustManager[]{new
			// X509TrustManager(){
			//
			// @Override
			// public void
			// checkClientTrusted(java.security.cert.X509Certificate[]
			// x509Certificates, String s) throws
			// java.security.cert.CertificateException {
			//
			// }
			//
			// @Override
			// public void
			// checkServerTrusted(java.security.cert.X509Certificate[]
			// x509Certificates, String s) throws
			// java.security.cert.CertificateException {
			//
			// }
			//
			// @Override
			// public java.security.cert.X509Certificate[] getAcceptedIssuers()
			// {
			//// return new java.security.cert.X509Certificate[0];
			// return null;
			// }
			// }}, new SecureRandom());

			return s_sSLContext;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
