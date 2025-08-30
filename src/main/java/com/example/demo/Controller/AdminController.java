package com.example.demo.Controller;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reports")
    public String adminReports(Model model) {
        model.addAttribute("reports", "Admin specific reports here");
        return "adminReports";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public String viewUsers(Model model) {
        // You would typically fetch user data here
        model.addAttribute("users", "User data");
        return "userList";
    }
}
