package com.personalproject.myblog.controller;

import com.personalproject.myblog.models.UserRoleEnum;
import com.personalproject.myblog.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {


    @GetMapping("/post-form")
    public String postForm() {
        return "postForm";
    }

    @GetMapping("/detail.html")
    public String detail(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "detail";
    }
}