package com.example.harikrishna.design1;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hari Krishna on 10-01-2017.
 */

public class java2 extends Application {
    public static final String TAG = java2.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static java2 mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        // initialize the singleton
        mInstance = this;
        //MultiDex.install(this);
    }

    public static synchronized java2 getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
