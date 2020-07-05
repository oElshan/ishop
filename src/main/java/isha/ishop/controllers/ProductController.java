package isha.ishop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProductController {

    @RequestMapping(value = "/subcategory/{value}" ,method = RequestMethod.GET)
    public  String showProducrBySubcategory(@PathVariable String value) {

        return "category-grid";
    }
}
