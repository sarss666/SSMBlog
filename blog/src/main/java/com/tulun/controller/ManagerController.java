package com.tulun.controller;

import com.tulun.model.Manager;
import com.tulun.model.Result;
import com.tulun.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    /**
     * 登录首页
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    //登录校验
    @RequestMapping("/logininfo")
    @ResponseBody
    public Result loginInfo(Manager manager, HttpSession session) {
        //到数据库中查询匹配
        boolean login = managerService.login(manager);
        if (login) {
            //登录成功
            //记录登录信息
            session.setAttribute("username", manager.getUsername());
            return new Result("success", "成功");
        } else {
            return new Result("fail", "密码有误，请重新登录");
        }

    }
}
