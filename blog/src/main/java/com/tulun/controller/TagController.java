package com.tulun.controller;

import com.tulun.model.Tag;
import com.tulun.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取标签列表
     * @return
     */
    @RequestMapping("/taglist")
    public ModelAndView tagList() {
        List<Tag> tags = tagService.loadAllTag();

        ModelAndView mv = new ModelAndView();
        //封装数据
        mv.addObject("tags", tags);
        //指定页面
        mv.setViewName("taglist");
        return mv;
    }
}
