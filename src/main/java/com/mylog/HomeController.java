package com.mylog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    //git test2
    @RequestMapping("")
    public String home() {
        return "redirect:/post/list";
    }
}