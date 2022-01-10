package com.cks.travelblog.repository;

import com.cks.travelblog.http.Blog;

import java.util.List;

public interface DataFromNetworkCallback {
    void onSuccess(List<Blog> blogList);
    void onError();
}