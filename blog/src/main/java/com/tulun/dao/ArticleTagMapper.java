package com.tulun.dao;

import com.tulun.model.ArticleTag;

import java.util.List;

public interface ArticleTagMapper {
    int insertArticleTag(ArticleTag articleTag);

    List<ArticleTag> selectArticleTagByArticleId(Integer articleId);
}
