package com.davids.android.newsapp;

/**
 * Created by krypt on 17/11/2016.
 */

public class News {

    private String mHeadline;

    private String mSection;

    private String mUrl;

    public News (String headline, String section, String url){
        mHeadline = headline;
        mSection = section;
        mUrl = url;
    }

    public String getHeadline() {
        return mHeadline;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }
}
