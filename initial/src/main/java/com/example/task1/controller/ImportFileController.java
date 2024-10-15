package com.example.task1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImportFileController {
    @RequestMapping("/import")
    public String welcome() {
        return "importFile";
    }
}