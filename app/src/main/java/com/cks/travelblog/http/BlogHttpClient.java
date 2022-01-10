package com.cks.travelblog.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class BlogHttpClient {
    public static final BlogHttpClient INSTANCE = new BlogHttpClient();

    public static final String BASE_URL = "https://github.com/Chong1455/TravelBlog";
    public static final String PATH = "/blob/master/travel-blog-resources";
    private static final String BLOG_ARTICLES_URL = BASE_URL + PATH + "/travel-api.json";

    private Executor executor;
    private OkHttpClient client;
    private Gson gson;

    private BlogHttpClient() {
        executor = Executors.newFixedThreadPool(4);
        client = new OkHttpClient();
        gson = new Gson();
    }

    public void loadBlogArticles(BlogArticlesCallback callback) {
        Request request = new Request.Builder()
                .get()
                .url(BLOG_ARTICLES_URL)
                .build();

        executor.execute(() -> {
            try {
                Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String json = responseBody.string();
                    BlogData blogData = gson.fromJson(json, BlogData.class);
                    if (blogData != null) {
                        callback.onSuccess(blogData.getData());
                        return;
                    }
                }
            } catch(IOException e) {
                Log.e("BlogHTTPClient", "Error loading blog articles");
            }
            callback.onError();
        });
    }
}
