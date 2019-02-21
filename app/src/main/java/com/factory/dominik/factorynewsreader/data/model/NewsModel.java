package com.factory.dominik.factorynewsreader.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NewsModel implements Parcelable{

    private String status;
    private String source;
    private String sortBy;
    private ArrayList<ArticlesModel> articles;




    protected NewsModel(Parcel in) {
        status = in.readString();
        source = in.readString();
        sortBy = in.readString();
        this.articles = new ArrayList<ArticlesModel>();
        in.readList(this.articles, ArticlesModel.class.getClassLoader());
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    public NewsModel(String status, String source, String sortBy, ArrayList<ArticlesModel> articles) {
        this.status = status;
        this.source = source;
        this.sortBy = sortBy;
        this.articles = articles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(source);
        dest.writeString(sortBy);
        dest.writeList(articles);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public ArrayList<ArticlesModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<ArticlesModel> articles) {
        this.articles = articles;
    }






}
