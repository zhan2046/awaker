package com.ruzhan.awaker.article.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AwakerClient {

    private static final String HOST = "http://www.awaker.cn/api/";

    private static AwakerApi api;

    private AwakerClient() {
    }

    public static AwakerApi get() {
        if (api == null) {
            synchronized (AwakerClient.class) {
                if (api == null) {
                    Retrofit client = new Retrofit.Builder().baseUrl(HOST)
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
