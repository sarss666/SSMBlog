package com.tulun.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Article {
    private Integer id;
    private Integer categoryId;
    private String title;
    private String content;
    private String description;
    private Integer statue;
    private String author;
    private Date createTime;
    private Integer showCount;
    //新增属性，处理博客的多标签
    private List<ArticleTag> tagList;
    //新增属性，处理事件展示
    private String time;
    //新增属性，展示博客类别
    private String categoryName;
    //新增属性，获取选择标签id
    private Integer tagId;
}
