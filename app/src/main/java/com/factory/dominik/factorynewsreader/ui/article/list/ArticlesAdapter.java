package com.factory.dominik.factorynewsreader.ui.article.list;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.factory.dominik.factorynewsreader.R;
import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public interface NewsAdapterListener {
        void onNewsClick(ArticlesModel article, int position);
    }

    private ArrayList<ArticlesModel> articles = new ArrayList<>();
    private NewsAdapterListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final ArticlesModel article = articles.get(position);
        viewHolder.titleTextView.setText(article.getTitle());
        viewHolder.descriptionTextView.setText(article.getDescription());
        Glide.with(viewHolder.imageView.getContext()).load(article.getUrlToImage()).centerCrop().into(viewHolder.imageView);
        viewHolder.newsItemLayout.setOnClickListener(v -> listener.onNewsClick(article,position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(List<ArticlesModel> articlesToAdd){
        this.articles.clear();
        this.articles.addAll(articlesToAdd);
        notifyDataSetChanged();
    }

    public ArrayList<ArticlesModel> getArticles(){
        return articles;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_textview)
        TextView titleTextView;
        @BindView(R.id.description_textview)
        TextView descriptionTextView;
        @BindView(R.id.imageview)
        ImageView imageView;
        @BindView(R.id.news_item_layout)
        ConstraintLayout newsItemLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public void setListener(NewsAdapterListener listener){this.listener = listener;}
}
