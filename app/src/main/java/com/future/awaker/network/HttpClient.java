package com.future.awaker.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Copyright ©2017 by Teambition
 */

public class HttpClient {

    private static HttpClient httpClient;

    private OkHttpClient okHttpClient;

    public static HttpClient get() {
        if (httpClient == null) {
            synchronized (HttpClient.class) {
                if(httpClient == null) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }

    private HttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static OkHttpClient getHttpClient() {
        return get().okHttpClient;
    }
}
