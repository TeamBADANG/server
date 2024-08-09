package com.gbcontentagency.arlo.apis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@ResponseBody
public class HelloController {

    @GetMapping("/")
    public String hello(Model model) {

        model.addAttribute("message", "Hi, there! This is Girin's world!");

        return "index";
    }

    @GetMapping("/healthcheck")
    public String healthcheck() {

        return "Hi, there! This is Girin's world!";
    }

}
