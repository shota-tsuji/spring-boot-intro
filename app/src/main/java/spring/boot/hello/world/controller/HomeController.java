package spring.boot.hello.world.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import spring.boot.hello.world.model.Invoice;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(@ModelAttribute Invoice invoice) {
        return "index";
    }

    @PostMapping("/")
    private String confirm(@Validated @ModelAttribute Invoice invoice, BindingResult result) {

        if (result.hasErrors()) {
            return "index";
        }

        return "confirm";
    }
}
