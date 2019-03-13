package com.shenlan.log_filter_demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @PostMapping("/connect")
    public String connect(String str) {
        return "request : " + str;
    }

}
