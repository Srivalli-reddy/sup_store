package subject.store.supplement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import subject.store.supplement.entities.User;
import subject.store.supplement.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; // register.jsp
    }

    // Handle user registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }
        userRepository.save(user);
        model.addAttribute("message", "Registration successful. Please login.");
        return "login";
    }

    // Show login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login
    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, HttpSession session, Model model) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }

        session.setAttribute("loggedInUser", existingUser);

        if ("ADMIN".equalsIgnoreCase(existingUser.getRole())) {
            return "redirect:/admin/dashboard";
        } else if ("CUSTOMER".equalsIgnoreCase(existingUser.getRole())) {
            return "redirect:/customer/dashboard";
        } else {
            model.addAttribute("error", "Unknown role.");
            return "login";
        }
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", user.getRole());
        return "dashboard";
    }

    // Customer Dashboard
    @GetMapping("/customer/dashboard")
    public String customerDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("username", user.getUsername());
        model.addAttribute("role", user.getRole());
        return "dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
