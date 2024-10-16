package com.example.task1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/import")
    public String importFile() {
        return "importFile";
    }

    @RequestMapping("/register")
    public String register() {
        return "registerJob";
    }
}