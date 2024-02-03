package online.aquan.shortlink.project.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 短链接不存在跳转控制器
 * Controller注解如果是返回字符串会去寻找视图
 */
@Controller
public class LinkNotFoundController {

    @RequestMapping("/page/notfound")
    public String notFound() {
        return "notfound";
    }
}
