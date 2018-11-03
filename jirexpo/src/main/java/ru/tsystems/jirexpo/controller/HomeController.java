package ru.tsystems.jirexpo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin
public class HomeController {

    /**
     * @return Start page.
     */
    @GetMapping("/jirexpo/")
    public String index() {
        return "index";
    }
}
