package com.swaroop.emojis.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class OkHttp3Factory {

    private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static OkHttpClient instance;

    public static synchronized OkHttpClient getOkHttpInstance(final Context context) {
        if (instance == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(15, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);

            File cacheDir = new File(context.getCacheDir(), "emojiscache");
            Cache cache = new Cache(cacheDir, CACHE_SIZE);

            okHttpClientBuilder.cache(cache);

            okHttpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (isNetworkAvailable(context)) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                }
            });

            instance = okHttpClientBuilder.build();
        }
        return instance;
    }

    private static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
