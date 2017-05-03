package com.iwintrue.todoapplication.io;

import org.xutils.common.Callback;

/**
 * Created by zhoukai on 2017/4/28.
 */

public class RequestCancelable {



    public RequestCancelable(Request request,Callback.Cancelable cancelable) {
        super();
        this.cancelable = cancelable;
        this.request = request;
    }

    public Callback.Cancelable cancelable;

    public Request request;

}
