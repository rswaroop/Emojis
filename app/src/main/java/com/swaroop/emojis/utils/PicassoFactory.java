package com.swaroop.emojis.utils;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executors;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class PicassoFactory {

    private static Picasso instance;

    public static synchronized Picasso getPicassoInstance(final Context context) {
        if (instance == null) {
            Picasso.Builder picassoBuilder = new Picasso.Builder(context);
            picassoBuilder.downloader(new OkHttp3Downloader(OkHttp3Factory.getOkHttpInstance(context)));
            picassoBuilder.executor(Executors.newSingleThreadExecutor());
            instance = picassoBuilder.build();
//            instance.setIndicatorsEnabled(true);
        }
        return instance;
    }
}
