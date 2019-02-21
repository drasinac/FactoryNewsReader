package com.factory.dominik.factorynewsreader.ui.article.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.factory.dominik.factorynewsreader.R;
import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ArrayList<ArticlesModel> articles;
    private int position;
    private String initialTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        ButterKnife.bind(this);
        getData();
        setupUi();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
       setTitle(articles.get(i).getTitle());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void getData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        articles = intent.getParcelableArrayListExtra("articles");
        initialTitle = intent.getStringExtra("title");
    }

    private void setupUi(){
        DetailsPagerAdapter mAdapter = new DetailsPagerAdapter(articles);
        viewPager.setOffscreenPageLimit(articles.size()-1);
        viewPager.setAdapter(mAdapter);
        progressBar.setVisibility(View.GONE);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(position);
        setTitle(initialTitle);
    }

}
