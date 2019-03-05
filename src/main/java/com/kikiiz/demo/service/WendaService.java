package com.kikiiz.demo.service;

import org.springframework.stereotype.Service;

@Service//所有变量的初始化不再通过编程人员负责，而是通过依赖注入（IoC），Spring的设计思想，类似享元模式
public class WendaService {
    public String getMessage(int userId){
        return "Hello Message:"+String.valueOf(userId);
    }
}
