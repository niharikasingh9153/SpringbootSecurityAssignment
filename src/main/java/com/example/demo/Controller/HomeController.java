package com.example.demo.Controller;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to the Library Management System");
        return "index"; // Template name
    }

    @GetMapping("/about")
    public String about() {
        return "about"; // Template name
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Template name
    }
}

