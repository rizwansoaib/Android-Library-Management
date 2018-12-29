package com.nautanki.loginregapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Santosh on 8/20/2016. First.
 */
public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    //Constructor
    private MySingleton(Context context){
        mCtx = context; //Initialize context.
        requestQueue = getRequestQueue(); //Call the method.
    }

    //Request queue method.
    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            //Initialize request queue.
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    //This method returns instance of this class.
    public static synchronized MySingleton getInstance(Context context){
        if (mInstance == null){
            //Initialize instance
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    //This method is to add request queue.
    public <T> void addToRequestque(Request<T> request){
        //Add each of the request to request queue.
        requestQueue.add(request);
    }
}