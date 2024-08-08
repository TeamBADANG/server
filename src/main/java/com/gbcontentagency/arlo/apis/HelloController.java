package com.gbcontentagency.arlo.apis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/healthcheck")
    public String healthcheck() {

        return "Hi, there! This is Girin's world";
    }

}
