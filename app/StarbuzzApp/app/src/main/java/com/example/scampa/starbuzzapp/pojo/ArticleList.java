
package com.example.scampa.starbuzzapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ArticleList {

    @SerializedName("articleList")
    @Expose
    private List<ArticleList_> articleList = new ArrayList<ArticleList_>();

    /**
     * 
     * @return
     *     The articleList
     */
    public List<ArticleList_> getArticleList() {
        return articleList;
    }

    /**
     * 
     * @param articleList
     *     The articleList
     */
    public void setArticleList(List<ArticleList_> articleList) {
        this.articleList = articleList;
    }



}
