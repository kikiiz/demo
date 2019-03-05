package com.kikiiz.demo.controller;

import com.kikiiz.demo.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SettingController {

    @Autowired
    WendaService wendaService;//不需要初始化了

    @RequestMapping(path={"/setting"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(){
        return "Setting OK !"+wendaService.getMessage(123);
    }
}
