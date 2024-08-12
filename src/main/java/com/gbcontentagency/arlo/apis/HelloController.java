package com.gbcontentagency.arlo.apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {

        model.addAttribute("title", "Girin World");
        model.addAttribute("message", "Hello, World! This is Girin's World!");

        return "index";
    }


    @GetMapping("/main")
    public String frontPage() {

        return "redirect:https://e6fe-118-176-146-86.ngrok-free.app";
    }

    @GetMapping("/my")
    public String test(Model model) {

        model.addAttribute("title", "Private Space");
        model.addAttribute("message", "Welcome! This is a private space");

        return "index";
    }

}
