package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {
    @GetMapping("/userPage")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String userPage() {
        return "userPage";
    }
}