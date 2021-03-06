package isha.store.controllers.client;

import isha.store.controllers.AbstractProductController;
import isha.store.entity.Product;
import isha.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/products")
public class ProductController extends AbstractProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ServletContext servletContext;

    @GetMapping(value = "/search")
    public String searchItemGrid(@RequestParam("search") String search, @RequestParam("page") Optional<Integer> page,
                                 @RequestParam(value = "price",required = false) BigDecimal[] price,
                                 @RequestParam(value = "producers",required = false) Set<Long> producers,
                                 Model model, HttpServletRequest request) {

        int currentPage = page.orElse(1);
        Page<Product> productsPage = productService.findProductBySearch(search,price,producers,currentPage,12);
        List<Product> products = productsPage.getContent();
        model.addAttribute("pageNumbers", pagination(productsPage));
        model.addAttribute("search", search);
        model.addAttribute("products", products);
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("breadcrumb", search);
        model.addAttribute("minMax", productService.getMinMaxPriceProductBySearchName(search));

//        model.addAttribute("filterProducersIdSet", filterProduct.getProducers());
//        model.addAttribute("isFilterPrice", true);

        if (page.orElse(null) != null) {

            if (request.getParameter("price") == null && request.getParameter("producers") == null) {
                model.addAttribute("urlPagination", request.getRequestURI() + "?search=" +
                        search);
            } else if (request.getParameter("price") == null && request.getParameter("producers") != null) {
                model.addAttribute("urlPagination", request.getRequestURI() + "?search=" +
                        request.getParameter("search")+"&producers="+request.getParameter("producers"));
                model.addAttribute("filterProducersIdSet", Arrays.stream(request.getParameter("price")
                        .split(",")).collect(Collectors.toSet()));

            } else if (request.getParameter("price") != null && request.getParameter("producers") == null) {
                model.addAttribute("urlPagination",
                        request.getRequestURI() + "?search=" +
                                request.getParameter("search") + "&price=" + request.getParameter("price"));
            }else  model.addAttribute("urlPagination",
                    request.getRequestURI()+"?search="+request.getParameter("search")+
                            "&price="+request.getParameter("price")+"&producers="+request.getParameter("producers"));

        } else model.addAttribute("urlPagination", request.getRequestURI() + '?'
                + request.getQueryString());
        request.getParameter("page");
        if (request.getParameter("price") != null) {

            model.addAttribute("filterPrice", request.getParameter("price").split(","));
        }
        if (request.getParameter("producers") != null) {
            model.addAttribute("filterProducersIdSet", Arrays.stream((request.getParameter("producers")
                    .split(","))).map(Long::parseLong) .collect(Collectors.toSet()));

        }

        model.addAttribute("producers", productService.getProducersBySearchProduct(search));

        return "product-grid";
    }

}
