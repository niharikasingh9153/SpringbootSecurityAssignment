package com.example.SpringSecurity.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports")
    public String getReports() {
        return "Admin reports displayed here.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of("admin", "lib123", "student", "guest");
    }
}

