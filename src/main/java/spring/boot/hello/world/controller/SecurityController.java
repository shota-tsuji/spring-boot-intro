package spring.boot.hello.world.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.boot.hello.world.model.SiteUser;
import spring.boot.hello.world.repository.SiteUserRepository;
import spring.boot.hello.world.util.Role;

@Controller
public class SecurityController {

    private final SiteUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityController(SiteUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String showList(Authentication loginUser, Model model) {
        model.addAttribute("username", loginUser.getName());
        model.addAttribute("role", loginUser.getAuthorities());
        return "user";
    }

    @GetMapping("/admin/list")
    public String showAdminList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "list";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("user") SiteUser user) {
        return "register";
    }

    @PostMapping("/register")
    public String process(@Validated @ModelAttribute("user") SiteUser user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.setRole(Role.ADMIN.name());
        } else {
            user.setRole(Role.USER.name());
        }
        userRepository.save(user);

        return "redirect:/login?register";
    }
}
