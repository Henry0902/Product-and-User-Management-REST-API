package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class testcontroller extends BaseController{
    @GetMapping(value = "/test")
    public String get(Model model) {
        return "test/test";
    }
}
