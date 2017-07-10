package com.davids.android.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by krypt on 17/11/2016.
 */

public class NewsLoader extends android.content.AsyncTaskLoader<List<News>> {
     private String mUrl;

    public NewsLoader (Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<News> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
