package com.gbcontentagency.arlo.apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {

        model.addAttribute("title", "Girin World");
        model.addAttribute("message", "Hi, there! This is Girin's world!");

        return "index";
    }

    @GetMapping("/admin")
    public String test(Model model) {

        model.addAttribute("title", "Girin World");
        model.addAttribute("message", "Hi, there! This is Admin Page");

        return "index";
    }

}
