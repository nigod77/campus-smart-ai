package com.nijiahao.llm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/llm")
@CrossOrigin
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
