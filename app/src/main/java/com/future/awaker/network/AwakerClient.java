package com.future.awaker.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright ©2017 by Teambition
 */

public final class AwakerClient {

    private static AwakerApi api;

    private AwakerClient() {}

    public static AwakerApi get() {
        if(api == null) {
            synchronized (AwakerClient.class) {
                if (api == null) {
                    Retrofit client = new Retrofit.Builder().baseUrl("http://www.awaker.cn/api/")
                            .client(HttpClient.getHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    api = client.create(AwakerApi.class);
                }
            }
        }
        return api;
    }
}
