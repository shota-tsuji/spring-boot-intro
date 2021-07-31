package spring.boot.hello.world.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.boot.hello.world.repository.CoffeeRepository;

@Scope("request")
@Controller
public class ScopeController {

    private final CoffeeRepository repository;

    public ScopeController(CoffeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/scope")
    public String showList(Model model) {
        model.addAttribute("toString", this.toString());
        model.addAttribute("allCoffee", repository.findAll());

        return "index";
    }
}
