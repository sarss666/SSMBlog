package com.tulun.dao;

import com.tulun.model.Article;
import com.tulun.util.Pager;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int insertArticle(Article article);

    Article selectArticleById(Integer id);

    int updateShowCount(Integer id);

    List<Article> selectAllArticles();

    List<Article> selectArticlesByCategoryId(Integer categoryId);

    List<Article> selectArticles(@Param("pager") Pager<Article> pager, @Param("article") Article article);

    int selectArticlesCount(Article article);

    int updateStatue(Article article);
}
