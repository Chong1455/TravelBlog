package com.cks.travelblog.repository;

import android.content.Context;

import androidx.room.Database;

import com.cks.travelblog.database.AppDatabase;
import com.cks.travelblog.database.BlogDAO;
import com.cks.travelblog.database.DatabaseProvider;
import com.cks.travelblog.http.Blog;
import com.cks.travelblog.http.BlogHttpClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlogRepository {

    private BlogHttpClient httpClient;
    private AppDatabase database;
    private Executor executor;

    public BlogRepository(Context context) {
        httpClient = BlogHttpClient.INSTANCE;
        database = DatabaseProvider.getInstance(context.getApplicationContext());
        executor = Executors.newSingleThreadExecutor();
    }

    public void loadDataFromDatabase(DataFromDatabaseCallback callback) {
        executor.execute(() -> callback.onSuccess(database.blogDao().getAll()));

    }

    public void loadDataFromNetwork(DataFromNetworkCallback callback) {
        executor.execute(() -> {
            List<Blog> blogList = httpClient.loadBlogArticles();
            if (blogList == null) {
                callback.onError();
            } else {
                BlogDAO blogDAO = database.blogDao();
                blogDAO.deleteAll();
                blogDAO.insertAll(blogList);
                callback.onSuccess(blogList);
            }
        });
    }
}