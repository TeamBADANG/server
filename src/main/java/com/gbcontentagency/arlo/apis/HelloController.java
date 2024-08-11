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

    @GetMapping("/healthcheck")
    public String healthcheck(Model model) {

        model.addAttribute("title", "Girin World");
        model.addAttribute("message", "This is healthCheck Page");

        return "index";
    }

    @GetMapping("/test")
    public String test(Model model) {

        model.addAttribute("title", "Girin World");
        model.addAttribute("message", "Hi, there! This is Test Page");

        return "index";
    }

}
