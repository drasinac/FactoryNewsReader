package com.factory.dominik.factorynewsreader.ui.article.list;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.factory.dominik.factorynewsreader.R;
import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;
import com.factory.dominik.factorynewsreader.data.service.ServiceProvider;
import com.factory.dominik.factorynewsreader.ui.article.details.ArticleDetailsActivity;
import com.factory.dominik.factorynewsreader.util.CacheHelper;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleListActivity extends AppCompatActivity implements ArticleListView, ArticlesAdapter.NewsAdapterListener {

    @BindView(R.id.news_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.error_textview)
    TextView errorTextView;

    private ArticlesAdapter articlesAdapter;
    private ArticleListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.GONE);
        setupUi();
        fetchData();
    }


    private void setupUi() {
        articlesAdapter = new ArticlesAdapter();
        articlesAdapter.setListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(articlesAdapter);
    }

    private void fetchData() {
        presenter = new ArticleListPresenter(this, ServiceProvider.getNewsService());
        if (CacheHelper.shouldFetchNewData(this, new Date().getTime())) {
            presenter.getNews();
        }else{
            showArticles(CacheHelper.loadArticles(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        errorTextView.setVisibility(View.GONE);
    }

    @Override
    public void showArticles(List<ArticlesModel> articles) {
        articlesAdapter.addArticles(articles);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void cacheArticles(List<ArticlesModel> articles) {
        long newCacheTime = new Date().getTime();
        CacheHelper.saveArticles(this, articles);
        CacheHelper.saveLastTime(this, newCacheTime);
    }

    @Override
    public void showArticlesFetchError(String message) {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        new AlertDialog.Builder(this, R.style.AlertDialogCustom).setTitle(R.string.error).setMessage(R.string.error_message).setPositiveButton(R.string.ok, (dialog, which) -> presenter.getNews()).show();
    }

    @Override
    public void onNewsClick(ArticlesModel article, int position) {
        Intent i = new Intent(ArticleListActivity.this, ArticleDetailsActivity.class);
        i.putExtra("url", article.getUrl());
        i.putExtra("title", article.getTitle());
        i.putExtra("position", position);
        i.putParcelableArrayListExtra("articles", articlesAdapter.getArticles());
        startActivity(i);
    }
}
