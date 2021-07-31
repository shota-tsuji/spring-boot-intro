package spring.boot.hello.world.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.boot.hello.world.repository.CoffeeRepository;

@Controller
public class HomeController {

    private final ApplicationContext context;

    public HomeController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping("/")
    public String showList(Model model) {
        CoffeeRepository repository = (CoffeeRepository) context.getBean("coffeeRepository");
        model.addAttribute("toString", this.toString());
        model.addAttribute("allCoffee", repository.findAll());

        return "index";
    }
}
