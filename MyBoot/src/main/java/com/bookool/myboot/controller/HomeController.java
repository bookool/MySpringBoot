package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口根路径
 *
 * @author Tommy
 */
@RestController
public class HomeController extends BaseController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "Hello World!";
    }
}
