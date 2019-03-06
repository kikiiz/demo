package com.kikiiz.demo.controller;

import com.kikiiz.demo.aspect.LogAspect;
import com.kikiiz.demo.model.User;
import com.kikiiz.demo.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    WendaService wendaService;

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession){
        logger.info("visit home");
        return wendaService.getMessage(2)+"Hello Dogs!"+httpSession.getAttribute("msg");
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                           @PathVariable("groupId") String groupId,
                           @RequestParam(value = "type",defaultValue = "1") int type,
                           @RequestParam(value="key",defaultValue = "Silly Dog",required = false) String key){
        return String.format("Profile Page of %s %d,type:%d,key:%s",groupId,userId,type,key);
    }

    /*
    向模板传递参数
     */
    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","abc");
        List<String> colors = Arrays.asList(new String []{"red","green","blue"});
        model.addAttribute("colors",colors);
        Map<String,String> map=new HashMap<>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user",new User("MiMi"));//注意用法
        return "home";
    }

    /*
    Request和Response
     */
    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody//带有ResponseBody 返回的是文本而非模板
    public String request(Model model, HttpServletResponse response,
                           HttpServletRequest request, HttpSession httpSession,
                          @CookieValue("JSESSIONID") String sessionId) {
        //Model:把参数传递到模板，此处不必须
        //@CookieValue直接可以将名为xxx的cookie的value读取成变量sessionId
        //request:用户向后台发出请求时包含的信息(参数解析，Cookie读取等)
        //response:后台返回给用户(页面内容返回，Cookie下发等)
        StringBuilder sb=new StringBuilder();

        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){//输出所有header的name和对应值
            String name=headerNames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }

        sb.append("CookieValueOfJSESSIONID:"+sessionId+"<br><br>");//直接读取某Cookie

        if(request.getCookies()!=null){//遍历显示cookie，可以分析用户的cookie转向不同功能
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie:"+cookie.getName()+" value:"+cookie.getValue()+"<br>");
            }
        }

        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");

        response.addHeader("SillyDog_Id","hello");
        response.addCookie(new Cookie("username","silly_dog"));
        return sb.toString();
    }

    /*
    重定向，此为302跳转，临时转移
     */
    @RequestMapping(path={"/redirect0/{code}"},method = {RequestMethod.GET})
    public String redirect0(@PathVariable("code") int code,HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");//跳转到其他页面时传递session
        return "redirect:/";
    }

    /*
    重定向，301跳转，强制性跳转，永久转移
     */
    @RequestMapping(path={"/redirect/{code}"},method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");//跳转到其他页面时传递session
        RedirectView red = new RedirectView("/",true);
        if(code==301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);//301
        }
        return red;//302
    }

    @RequestMapping(path={"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "Hello admin! ";
        }
        throw new IllegalArgumentException("非管理员");//抛出异常
    }
    /*
    定义异常统一处理
     */
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }
}
