package com.gbcontentagency.arlo.apis;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/healthcheck")
    public String healthcheck() {

        return "ok";
    }

}
