package com.davids.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by krypt on 17/11/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView headlineTextView = (TextView) listItemView.findViewById(R.id.headline);
        headlineTextView.setText(currentNews.getHeadline());

        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section);
        sectionTextView.setText(currentNews.getSection());

        return listItemView;
    }
}
