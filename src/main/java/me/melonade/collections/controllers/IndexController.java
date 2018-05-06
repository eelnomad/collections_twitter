package me.melonade.collections.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by damon on 8/29/2016.
 * Controller for Index.html
 */

@Controller
public class IndexController {

    @RequestMapping(value = {"/"})
    public String index() {
        return "index.html";
    }
}
