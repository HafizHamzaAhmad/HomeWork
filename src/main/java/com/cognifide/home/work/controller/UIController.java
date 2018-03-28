package com.cognifide.home.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UIController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
