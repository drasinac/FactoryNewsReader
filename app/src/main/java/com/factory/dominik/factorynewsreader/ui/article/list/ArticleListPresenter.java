package com.factory.dominik.factorynewsreader.ui.article.list;

import com.factory.dominik.factorynewsreader.data.model.ArticlesModel;
import com.factory.dominik.factorynewsreader.data.service.ServiceInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ArticleListPresenter {

    private ArticleListView articleListView;
    private ServiceInterface service;
    private CompositeDisposable disposable;

    ArticleListPresenter(ArticleListView view, ServiceInterface service) {
        this.articleListView = view;
        this.service = service;
        disposable = new CompositeDisposable();
    }

    void getNews() {
        disposable.add(service.getNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> parseArticles(resp.getArticles()), throwable -> articleListView.showArticlesFetchError(throwable.getMessage())));
    }

    private void parseArticles(ArrayList<ArticlesModel> articlesToParse) {
        disposable.add(Observable.fromIterable(articlesToParse)
                .flatMap(this::parseArticle)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parsedArticles -> {
                    articleListView.showArticles(parsedArticles);
                    articleListView.cacheArticles(parsedArticles);
                }, throwable -> articleListView.showArticlesFetchError(throwable.getMessage())));
    }

    private Observable<ArticlesModel> parseArticle(ArticlesModel article) {
        final StringBuilder builder = new StringBuilder();
        try {
            Document doc = Jsoup.connect(article.getUrl()).get();
            Elements links = doc.select("p");
            boolean start;
            for (Element link : links) {
                start = true;
                if (link.attr("class").equals("twite__title")) {
                    start = false;
                } else if (link.attr("class").equals("twite__new-window")) {
                    start = false;
                } else if (link.attr("aria-hidden").equals("true")) {
                    start = false;
                } else if (link.attr("class").equals("twite__copy-text")) {
                    start = false;
                } else if (link.attr("class").equals("vxp-share__copy-text")) {
                    start = false;
                } else if (link.attr("class").equals("top-stories-promo-story__summary ")) {
                    start = false;
                } else if (link.attr("class").equals("heron__item-summary")) {
                    start = false;
                }

                if (start) {
                    builder.append(link.text()).append("\n");
                }
            }

        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }
        article.setArticle(builder.toString());
        return Observable.just(article);
    }

}
