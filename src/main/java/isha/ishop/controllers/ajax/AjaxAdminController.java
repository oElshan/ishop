package isha.ishop.controllers.ajax;

import isha.ishop.entity.ClientOrder;
import isha.ishop.entity.Status;
import isha.ishop.form.EditOrder;
import isha.ishop.form.SearchForm;
import isha.ishop.model.OrderStatus;
import isha.ishop.services.ProductService;
import isha.ishop.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AjaxAdminController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    ProductService productService;

    @RequestMapping(path = "/ajax/admin/orderStatus", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public OrderStatus deleteItemFromShoppingCartView(HttpSession session, Model model) {

//        List<ClientOrder> clientOrderList = orderService.getAllNewOrders();
        OrderStatus orderStatus = new OrderStatus();
        long count = orderService.getCountNewOrders("new");
        System.out.println("---------"+count);
        if (count > 0) {
            orderStatus.setMessage("You have new order!");
            orderStatus.setCount(count);

        }
        return orderStatus;
    }


    @RequestMapping(path = "/ajax/admin/edit-order", method = RequestMethod.GET)
    public ModelAndView editOrder(@RequestParam long id, ModelMap model) {

        ClientOrder clientOrder = orderService.findClientOrderById(id);
        model.addAttribute("clientOrder", clientOrder);

        return new ModelAndView("fragment/editOrderModal :: editOrderModal", model);
    }

    @RequestMapping(path = "/ajax/admin/edit-order", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView changeOrder(@Valid @ModelAttribute EditOrder editOrder, ModelMap model) {
        System.out.println(editOrder);


        return new ModelAndView("fragment/editOrderModal :: editOrderModal", model);
    }

    @RequestMapping(path = "/ajax/admin", method = RequestMethod.GET)
    public String showSelectOrders(@RequestParam("page") Optional<Integer> page, @RequestParam("select") Optional<String> select, Model model) {
        int currentPage = page.orElse(1);
        String currentSelect = select.orElse("all");
        Page<ClientOrder> clientOrdersPage;
        clientOrdersPage = currentSelect.equalsIgnoreCase("all") ? orderService.getOrdersLimit(currentPage, 10) :  orderService.getOrdersLimit(currentPage, 10, currentSelect);
        List<Status> statuses = orderService.getAllStatusOrders();
        int totalPages = clientOrdersPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("statuses", statuses);
        model.addAttribute("clientOrdersPage",clientOrdersPage) ;
        model.addAttribute("orders", clientOrdersPage.getContent());

        return  "fragment/dataLatestOrder :: AjaxDataLatestOrder";
    }


    @RequestMapping( value = "/ajax/json/search-orders",method = RequestMethod.POST,produces = "application/json; charset=UTF-8" )
    public ModelAndView searchOrders(@RequestBody SearchForm search, ModelMap modelMap) {

        System.out.println(search);
        List<ClientOrder> orders = orderService.findOrderByName(search.getSearchName());
        modelMap.addAttribute("orders", orders);

        return new ModelAndView("fragment/dataTableOrders :: dataTableOrders", modelMap);
    }
}