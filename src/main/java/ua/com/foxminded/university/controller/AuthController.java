package ua.com.foxminded.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "redirect:/schedule/list/rooms";
    }

    @GetMapping("/forbidden")
    public String getForbiddenPage() {
        return "auth/forbidden";
    }


}
