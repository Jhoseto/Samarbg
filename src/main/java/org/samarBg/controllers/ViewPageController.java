package org.samarBg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewPageController {



    @GetMapping("/index")
    public String showRegister(){
        return "index";
    }

}