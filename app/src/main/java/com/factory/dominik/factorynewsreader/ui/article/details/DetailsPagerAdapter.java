package com.factory.dominik.factorynewsreader.ui.article.details;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.factory.dominik.factorynewsreader.R;

import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsPagerAdapter extends PagerAdapter {

    private ArrayList<ArticlesModel> articles = new ArrayList<>();

    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.title_textview)
    TextView titleTextView;
    @BindView(R.id.article_textview)
    TextView articleTextView;

    public DetailsPagerAdapter(ArrayList<ArticlesModel> articles){
        this.articles.clear();
        this.articles.addAll(articles);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ArticlesModel article = articles.get(position);
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.article_page,container,false);
        ButterKnife.bind(this,view );
        Glide.with(container.getContext()).load(article.getUrlToImage()).into(imageView);
        titleTextView.setText(article.getTitle());
        if (article.getArticle()!= null) {
            articleTextView.setText(article.getArticle());
        } else {
            articleTextView.setText(article.getDescription());
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
