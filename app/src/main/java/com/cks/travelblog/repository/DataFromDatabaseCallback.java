package com.cks.travelblog.repository;

import com.cks.travelblog.http.Blog;

import java.util.List;

public interface DataFromDatabaseCallback {
    void onSuccess(List<Blog> blogList);
}