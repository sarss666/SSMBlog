package com.tulun.model;

import lombok.Data;

@Data
public class ArticleTag {
    private Integer articleId;
    private Integer tagId;
    //新增属性，标签名称
    private String tagName;


}
