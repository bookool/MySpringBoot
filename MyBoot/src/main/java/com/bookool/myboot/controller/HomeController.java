package com.bookool.myboot.controller;

import com.bookool.myboot.common.base.BaseController;
import com.bookool.myboot.common.enums.response.UserResponseEnum;
import com.bookool.myboot.common.enums.response.base.CommonResponseEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    @Profile({"develop"})
    @RequestMapping(value = "/code_readme", method = RequestMethod.GET)
    public String codeReadme() {
        Map<String, Class<?>> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("通用", CommonResponseEnum.class);
        linkedHashMap.put("用户", UserResponseEnum.class);
        // TODO 此处继续增加响应枚举类
        StringBuilder headString = new StringBuilder();
        StringBuilder bodyString = new StringBuilder();
        try {
            Set<Map.Entry<String, Class<?>>> set = linkedHashMap.entrySet();
            Iterator<Map.Entry<String, Class<?>>> iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                String key = (String) entry.getKey();
                Class<?> value = (Class<?>) entry.getValue();
                headString.append("<a href='#").append(key).append("'>").append(key).append("</a> &nbsp; ");
                bodyString.append(enumToString(value, key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<html><head><title>响应code说明</title></head><body>" +
                headString.toString() +
                "<br />" +
                bodyString.toString() +
                "</body></html>";
    }

    private String enumToString(Class<?> clazz, String name) throws Exception {
        Object[] objects = clazz.getEnumConstants();
        Method getCode = clazz.getMethod("code");
        Method getName = clazz.getMethod("message");
        StringBuilder stringBuilder = new StringBuilder("<h3 id=\"")
                .append(name)
                .append("\">")
                .append(name)
                .append("</h3><table border=\"1\" cellspacing=\"0\" cellpadding=\"8\"><tr><th>code</th><th>message</th></tr>");
        for (Object obj : objects) {
            stringBuilder.append("<tr><td>")
                    .append(getCode.invoke(obj))
                    .append("</td><td>")
                    .append(getName.invoke(obj))
                    .append("</td></tr>");
        }
        stringBuilder.append("</table><br /><br />");
        return stringBuilder.toString();
    }

}