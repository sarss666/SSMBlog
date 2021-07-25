package com.tulun.controller;

import com.tulun.model.Article;
import com.tulun.model.Category;
import com.tulun.model.Result;
import com.tulun.model.Tag;
import com.tulun.service.ArticleService;
import com.tulun.service.CategoryService;
import com.tulun.service.TagService;
import com.tulun.util.JsonUtil;
import com.tulun.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Controller
public class ArticleController {
    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    /**
     * 新增博客页面
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView add() {
        ModelAndView mv = new ModelAndView();

        //指定数据，需要数据库中有数据
        mv.addObject("categoryList", categoryService.loadAllCategory());
        mv.addObject("tagList", tagService.loadAllTag());

        //指定页面
        mv.setViewName("/addarticle");
        return mv;
    }

    /**
     * 博客保存，返回JSON数据
     * @param param
     * @param content
     * @param description
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/addarticle")
    @ResponseBody
    public Result addArticle(String param, String content, String description, HttpSession session) throws UnsupportedEncodingException {
//        System.out.println("请求参数");
//        System.out.println("param：" + param);
//        System.out.println("content：" + content);
//        System.out.println("description：" + description);

        //进行解码
        param = URLDecoder.decode(param, "UTF-8");
        content = URLDecoder.decode(content, "UTF-8");
        description = URLDecoder.decode(description, "UTF-8");

//        System.out.println("解析后");
//        System.out.println("param：" + param);
//        System.out.println("content：" + content);
//        System.out.println("description：" + description);

        //JSON字符串转化为对象
        Article article = JsonUtil.fromJson(param, Article.class);
        article.setContent(content);
        article.setDescription(description);

        //将新增博客中的作者作为登录用户
        String username = (String) session.getAttribute("username");
        article.setAuthor(username);

        //填充默认值
        article.setCreateTime(new Date());
        article.setStatue(0);
        article.setShowCount(0);

        //插入数据库
        articleService.addArticle(article);
//        System.out.println(article);

        return new Result("success", "成功");
    }

    /**
     * 获取博客详情
     * @param id
     * @return
     */
    @RequestMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();
        //默认不存在页面
        mv.setViewName("notarticle");
        //参数的合法性
        if (id < 1) {
            //不合法的数据，返回一个提醒页面
            return mv;
        }

        //获取数据
        Article article = articleService.articleDetail(id);
        if (article == null || article.getId() == null) {
            //数据不存在
            return mv;
        }

        //阅读量加1
        articleService.updateShowCount(article.getId());

        mv.addObject("article", article);
        //指定页面
        mv.setViewName("articledetail");

        return mv;
    }

    /**
     * 后台首页
     * @return
     */
    @RequestMapping("/back")
    public ModelAndView back() {
        ModelAndView mv = new ModelAndView();

        List<Category> categories = categoryService.loadAllCategory();
        List<Tag> tags = tagService.loadAllTag();

        mv.addObject("categoryList", categories);
        mv.addObject("tagList", tags);

        //指定页面
        mv.setViewName("backarticle");
        return mv;
    }

    /**
     * 后台博客列表数据获取（通过参数过滤器符合条件数据）
     * @param pager
     * @param param
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/article/load")
    public ModelAndView articleLoad(Pager<Article> pager, String param) throws UnsupportedEncodingException {
        //字符串是通过JSON处理了，通过JSON将字符串转化为对象
        Article article = JsonUtil.fromJson(param, Article.class);

        //在传递标题参数时，对标题进行解码处理
        if (article != null && article.getTitle() != null && !"".equals(article.getTitle())) {
            String title = URLDecoder.decode(article.getTitle(), "UTF-8");
            article.setTitle(title);
        }
//        System.out.println("请求参数：" + article);

        //查询出符合条件的数据总个数（分页逻辑）
        int totalCount = articleService.loadArticlesCount(article);
        pager.setTotalCount(totalCount);

        //通过传递的类别、标签、名称来查询数据
        List<Article> articles = articleService.loadArticles(pager, article);
//        System.out.println(articles);

        ModelAndView mv = new ModelAndView();

        //数据
        mv.addObject("articleList", articles);

        //指定页面
        mv.setViewName("backarticlelist");

        return mv;
    }

    /**
     * 修改状态
     * @param id
     * @param statue
     * @return
     */
    @RequestMapping("/updatestatus")
    @ResponseBody
    public Result updateStatus(Integer id, Integer statue) {
        Article article = new Article();
        article.setId(id);
        article.setStatue(statue);

        articleService.updateStatue(article);

        return new Result("success", "修改成功");
    }
}
