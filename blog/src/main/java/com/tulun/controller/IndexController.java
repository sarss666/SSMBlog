package com.tulun.controller;

import com.tulun.model.Article;
import com.tulun.model.Category;
import com.tulun.model.Tag;
import com.tulun.service.ArticleService;
import com.tulun.service.CategoryService;
import com.tulun.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();



























        //类别列表
        List<Category> categories = categoryService.loadAllCategory();

        //标签标签
        List<Tag> tags = tagService.loadAllTag();

        mv.addObject("categoryList", categories);
        mv.addObject("tagList", tags);
        //封装页面
        mv.setViewName("index");
        return mv;
    }

    /**
     * 按类别进行前端博客列表获取（内部列表）
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("/loadPage/{categoryId}")
    public ModelAndView loadPage(@PathVariable("categoryId") Integer categoryId) {
        ModelAndView mv = new ModelAndView();
        List<Article> articles;
        if (categoryId == -1) {
            //特殊值，获取所有的博客列表
            articles = articleService.loadAllArticles();
//            mv.addObject("articleList", articles);
        } else {
            //按照不同的类别进行加载
            articles = articleService.loadCategoryArticle(categoryId);
        }

        mv.addObject("articleList", articles);
        mv.setViewName("articlepager");
        return mv;
    }

    /**
     * 按照类别进滚相关页面获取（区别与上一个url：在左侧类别展示上会有不同）
     *
     * @param categoryId
     * @return
     */
    @RequestMapping("/loadPageCategory/{categoryId}")
    public ModelAndView loadPageCategory(@PathVariable("categoryId") Integer categoryId) {
        ModelAndView mv = new ModelAndView();

        //类别列表
        List<Category> categories = categoryService.loadAllCategory();

        //通过泪飙id获取当前类别实例
        Category category = categoryService.loadCategoryById(categoryId);

        //标签标签
        List<Tag> tags = tagService.loadAllTag();

        mv.addObject("categoryList", categories);
        mv.addObject("tagList", tags);
        mv.addObject("ca", category);

        //指定页面
        mv.setViewName("articlepager1");

        return mv;
    }
}
