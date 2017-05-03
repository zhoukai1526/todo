package com.iwintrue.todoapplication.io;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;


/**
 * Created by dean on 16/5/19.
 */
public class UploadRequest {

    public <T> Callback.Cancelable execute(final Request.RequestCallback<T> callback){

        RequestParams requestParams = new RequestParams(Request.SERVER+"/api/content/uploadContentImage");

        requestParams.setMultipart(true);

//        requestParams.addBodyParameter();

        x.http().post(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File file) {

            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

        return null;
    }

}
