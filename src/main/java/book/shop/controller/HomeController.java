package book.shop.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class HomeController {
    @GetMapping(value = "/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
