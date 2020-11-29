package com.algonquin.cst8276.triggers.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.algonquin.cst8276.triggers.app.model.Order;
import com.algonquin.cst8276.triggers.app.service.OrderService;
import com.algonquin.cst8276.triggers.app.service.ProductService;

@Controller
public class OrderController {

    private OrderService orderService;

    private ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

	@GetMapping(value = { "/", "/home", "/index" })
	public String menuGet() {
		return "menu";
	}

    @GetMapping("/orders")
    public String ordersGet(Model model) {
        model.addAttribute("orders", orderService.findAllOrderDtos());

        return "order/all-orders";
    }

    @GetMapping("/orders/new")
    public String newOrderGet(Model model) {
        model.addAttribute("products", productService.findAllProductNamesPrices());

        Order order = new Order();
        order.setQuantity(1);
        order.setProductId(1L);

        model.addAttribute("order", order);

        return "order/new-order";
    }

    @PostMapping("/orders/new")
    public String newOrderPost(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("products", productService.findAllProductNamesPrices());

            return "order/new-order";
        }

        orderService.createOrder(order.getCustomerName(), order.getQuantity(), order.getProductId());

        return "redirect:/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String editOrderGet(@PathVariable("id") Long orderId, Model model) {
        Order order = orderService.findOrderById(orderId);

        model.addAttribute("order", order);
        model.addAttribute("product", productService.findProductDtoById(order.getProductId()));

        return "order/edit-order";
    }

    @PostMapping("/orders/edit")
    public String editOrderPost(@RequestParam("orderId") Long orderId, @RequestParam("quantity") Integer qty) {
        orderService.updateOrder(orderId, qty);

        return "redirect:/orders";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrderGet(@PathVariable("id") Long orderId, Model model) {
        model.addAttribute("order", orderService.findOrderDtoById(orderId));

        return "order/delete-order";
    }

    @PostMapping("orders/delete")
    public String deleteOrderPost(@RequestParam("orderId") Long orderId) {
        orderService.deleteOrder(orderId);

        return "redirect:/orders";
    }

}
