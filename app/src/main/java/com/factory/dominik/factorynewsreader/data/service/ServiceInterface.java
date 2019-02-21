package com.factory.dominik.factorynewsreader.data.service;
import com.factory.dominik.factorynewsreader.data.model.NewsModel;

import io.reactivex.Single;

import retrofit2.http.GET;


public interface ServiceInterface {

    @GET("/v1/articles?apiKey=6d8c542302124650bdb5a79ffbbf143a&sortBy=top&source=bbc-news")
    Single<NewsModel> getNews();
}
