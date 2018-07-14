package com.liheng.cateen.net;

import com.liheng.cateen.net.Api.GankApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Li
 * 2018/6/15.
 */

public class Network {

    private static GankApi mGankApi;
    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static GankApi getGankApi(){
        if (mGankApi == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mGankApi = retrofit.create(GankApi.class);
        }
        return mGankApi;
    }

}
