package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
    @GetMapping("/adminPage")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // ROLE_ADMINのユーザのみアクセスを許可
    public String adminPage() {
        return "adminPage";
    }
}