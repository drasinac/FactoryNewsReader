package com.factory.dominik.factorynewsreader.ui.article.list;

import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;

import java.util.List;

public interface ArticleListView {

    void showArticles(List<ArticlesModel> articles);

    void cacheArticles(List<ArticlesModel> articles);

    void showArticlesFetchError(String message);
}
