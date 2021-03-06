package com.cks.travelblog.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class BlogHttpClient {
    public static final BlogHttpClient INSTANCE = new BlogHttpClient();


    public static final String BASE_URL = "https://chong1455.github.io/TravelBlog";
    public static final String PATH = "/travel-blog-resources";
    private static final String BLOG_ARTICLES_URL = BASE_URL + PATH + "/travel-api.json";

    private Executor executor;
    private OkHttpClient client;
    private Gson gson;

    private BlogHttpClient() {
        executor = Executors.newFixedThreadPool(4);
        client = new OkHttpClient();
        gson = new Gson();
    }

    public List<Blog> loadBlogArticles() {
        Request request = new Request.Builder()
                .get()
                .url(BLOG_ARTICLES_URL)
                .build();

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String json = responseBody.string();
                BlogData blogData = gson.fromJson(json, BlogData.class);
                if (blogData != null) {
                    return blogData.getData();
                }
            }
        } catch (IOException e) {
            Log.e("BlogHTTPClient", "Error loading blog articles");
        }
        return null;
    }

}
