package com.example.scampa.starbuzzapp.services;

import com.example.scampa.starbuzzapp.pojo.ArticleList;
import com.example.scampa.starbuzzapp.pojo.ArticleList_;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
/**
 * Created by pablo on 25/3/2016.
 */


public final class SampleTestService {
    public static final String API_URL = "http://hmkcode.appspot.com/rest/controller/";

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

    public interface ArticlesService {
        @GET("get.json")
        Call<ArticleList> articles();
    }

    public static void main(String... args) throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        ArticlesService articlesService = retrofit.create(ArticlesService.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<ArticleList> call = articlesService.articles();

        // Fetch and print a list of the contributors to the library.
        ArticleList articleList = call.execute().body();
        for (ArticleList_ articleList_ : articleList.getArticleList()) {
            System.out.println(articleList_.getTitle() + " (" + articleList_.getUrl() + ")");
        }
    }
}