package com.cks.travelblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cks.travelblog.http.Blog;
import com.cks.travelblog.http.BlogArticlesCallback;
import com.cks.travelblog.http.BlogHttpClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class BlogDetailActivity extends AppCompatActivity {

    private static final String EXTRAS_BLOG = "EXTRAS_BLOG";

    private TextView textTitle;
    private TextView textDate;
    private TextView textAuthor;
    private TextView textRating;
    private TextView textDescription;
    private TextView textViews;
    private RatingBar ratingBar;
    private ImageView imageAvatar;
    private ImageView imageMain;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);

        textTitle = findViewById(R.id.textTitle);
        textDate = findViewById(R.id.textDate);
        textAuthor = findViewById(R.id.textAuthor);
        textRating = findViewById(R.id.textRating);
        textViews = findViewById(R.id.textViews);
        textDescription = findViewById(R.id.textDescription);
        ratingBar = findViewById(R.id.ratingBar);
        imageMain = findViewById(R.id.imageMain);
        imageAvatar = findViewById(R.id.imageAvatar);
        progressBar = findViewById(R.id.progressBar2);

        ImageView imageBack = findViewById(R.id.imageBack);
        imageBack.setOnClickListener(v -> finish());

        showData(getIntent().getExtras().getParcelable(EXTRAS_BLOG));
    }

    private void showData(Blog blog) {
        progressBar.setVisibility(View.GONE);
        ratingBar.setVisibility(View.VISIBLE);
        textTitle.setText(blog.getTitle());
        textDate.setText(blog.getDate());
        textAuthor.setText(blog.getAuthor().getName());
        textRating.setText(String.valueOf(blog.getRating()));
        textViews.setText(String.format("(%d views)", blog.getViews()));
        textDescription.setText(Html.fromHtml(blog.getDescription()));
        ratingBar.setRating(blog.getRating());

        Glide.with(this)
                .load(blog.getImageURL())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageMain);

        Glide.with(this)
                .load(blog.getAuthor().getAvatarURL())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }

    public static void startBlogDetailActivity(Activity activity, Blog blog) {
        Intent intent = new Intent(activity, BlogDetailActivity.class);
        intent.putExtra(EXTRAS_BLOG, (Parcelable) blog);
        activity.startActivity(intent);
    }

}