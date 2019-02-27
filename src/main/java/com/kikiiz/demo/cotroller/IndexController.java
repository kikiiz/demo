package com.kikiiz.demo.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(){
        return "Hello Dogs!";
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                           @PathVariable("groupId") String groupId,
                           @RequestParam(value = "type",defaultValue = "1") int type,
                           @RequestParam(value="key",defaultValue = "Silly Dog",required = false) String key){
        return String.format("Profile Page of %s %d,type:%d,key:%s",groupId,userId,type,key);
    }

    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","abc");
        return "home";
    }
}
