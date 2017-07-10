package com.davids.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krypt on 17/11/2016.
 */

public class QueryUtils{
    private static String LOG_TAG = News.class.getSimpleName();

    private QueryUtils(){}

    public static List<News> fetchNewsData (String requestUrl){
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Error fetching the news data", e);
        }

        List<News> news = extractFeatureFromJson(jsonResponse);
        return news;
    }

    private static List<News> extractFeatureFromJson(String newsJson) {

        if(TextUtils.isEmpty(newsJson)){
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(newsJson);

            JSONObject response= baseJsonResponse.getJSONObject("response");

            JSONArray resultsArray = response.getJSONArray("results");

            for(int i = 0; i < resultsArray.length(); i++){

                JSONObject currentNews = resultsArray.getJSONObject(i);

                String headline = currentNews.getString("webTitle");
                String section = currentNews.getString("sectionName");
                String url = currentNews.getString("webUrl");

                News story = new News(headline, section, url);

                news.add(story);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON", e);
        }

        return news;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();

            }
        }

        return jsonResponse;


    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        }catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error retrieving url", e);
        }

        return url;
    }
}
