package com.tulun.service;

import com.tulun.dao.ArticleMapper;
import com.tulun.dao.ArticleTagMapper;
import com.tulun.dao.CategoryMapper;
import com.tulun.model.Article;
import com.tulun.model.ArticleTag;
import com.tulun.model.Category;
import com.tulun.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 插入文章
     * @param article
     * @return
     */
    public int addArticle(Article article) {
        //需要将数据插入到t_article数据库，然后再插入博客标签关系，t_article_tag数据库（博客id和标签id）
        /*需要考虑的问题
            1.mybatis处理中如何获取数据库自增id的问题
            2.数据库事务的问题
         */
//        System.out.println("插入前：" + article);
        articleMapper.insertArticle(article);
        //获取插入的博客id
        Integer articleId = article.getId();
//        System.out.println("插入后：" + article);

        //还需要将博客和标签id插入到博客标签表
        List<ArticleTag> tagList = article.getTagList();
        Iterator<ArticleTag> iterator = tagList.iterator();
        while (iterator.hasNext()) {
            ArticleTag articleTag = iterator.next();
            articleTag.setArticleId(articleId);

            //插入到对应数据库
            articleTagMapper.insertArticleTag(articleTag);
        }

        return 1;
    }

    /**
     * 通过博客id查询博客详情
     * @param id
     * @return
     */
    public Article articleDetail(Integer id) {
        Article article = articleMapper.selectArticleById(id);
        if (article == null || article.getId() == null) {
            return article;
        }

        //将Date类型数据转化为String类型
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(article.getCreateTime());
        article.setTime(time);

        //通过类别id获取类别的名称
        Category category = categoryMapper.selectCategoryById(article.getCategoryId());
        article.setCategoryName(category.getCategoryName());

        //通过博客id获取标签列表
        List<ArticleTag> articleTags = articleTagMapper.selectArticleTagByArticleId(article.getId());
        article.setTagList(articleTags);

        return article;
    }

    /**
     * 阅读量变更
     * @param id
     * @return
     */
    public int updateShowCount(Integer id) {
        return articleMapper.updateShowCount(id);
    }

    //获取所有博客信息
    public List<Article> loadAllArticles() {
        List<Article> articles = articleMapper.selectAllArticles();
        if (articles != null && articles.size() > 0) {
            Iterator<Article> iterator = articles.iterator();
            while (iterator.hasNext()) {
                Article article = iterator.next();
                //将Date类型数据转化为String类型
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(article.getCreateTime());
                article.setTime(time);

                //通过类别id获取类别的名称
                Category category = categoryMapper.selectCategoryById(article.getCategoryId());
                article.setCategoryName(category.getCategoryName());

                //通过博客id获取标签列表
                List<ArticleTag> articleTags = articleTagMapper.selectArticleTagByArticleId(article.getId());
                article.setTagList(articleTags);
            }
        }
        return articles;
    }

    /**
     * 按照类别加载列表
     * @param categoryId
     * @return
     */
    public List<Article> loadCategoryArticle(Integer categoryId) {
        List<Article> articles = articleMapper.selectArticlesByCategoryId(categoryId);
        if (articles != null && articles.size() > 0) {
            Iterator<Article> iterator = articles.iterator();
            while (iterator.hasNext()) {
                Article article = iterator.next();
                //将Date类型数据转化为String类型
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(article.getCreateTime());
                article.setTime(time);

                //通过类别id获取类别的名称
                Category category = categoryMapper.selectCategoryById(article.getCategoryId());
                article.setCategoryName(category.getCategoryName());

                //通过博客id获取标签列表
                List<ArticleTag> articleTags = articleTagMapper.selectArticleTagByArticleId(article.getId());
                article.setTagList(articleTags);
            }
        }
        return articles;
    }

    /**
     * 通过查询条件来获取符合条件数据
     * @param a
     * @return
     */
    public List<Article> loadArticles(Pager<Article> pager, Article a) {
        List<Article> articles = articleMapper.selectArticles(pager, a);
        if (articles != null && articles.size() > 0) {
            Iterator<Article> iterator = articles.iterator();
            while (iterator.hasNext()) {
                Article article = iterator.next();
                //将Date类型数据转化为String类型
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(article.getCreateTime());
                article.setTime(time);

                //通过类别id获取类别的名称
                Category category = categoryMapper.selectCategoryById(article.getCategoryId());
                article.setCategoryName(category.getCategoryName());
            }
        }
        return articles;
    }

    /**
     * 查询符合条件的数据个数
     * @param article
     * @return
     */
    public int loadArticlesCount(Article article) {
        return articleMapper.selectArticlesCount(article);
    }

    public int updateStatue(Article article) {
        return articleMapper.updateStatue(article);
    }
}
