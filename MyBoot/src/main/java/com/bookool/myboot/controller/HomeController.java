package com.bookool.myboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tommy
 * @date 2018-08-05
 */
@RestController
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }
}
