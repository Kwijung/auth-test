package com.example.authtest.controller; // 패키지명 변경

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/custom-login")
    public String login() {
        return "login"; // login.html 템플릿 반환
    }
}
