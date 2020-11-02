package com.algonquin.cst8276.triggers.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.algonquin.cst8276.triggers.app.service.ProductService;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/inventory")
    public String inventoryGet(Model model) {
        model.addAttribute("products", productService.findAllProductDtos());
        return "product/all-products";
    }

    @GetMapping("/inventory/add/{id}")
    public String addInventory(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("product", productService.findProductDtoById(productId));

        return "product/add-inventory";
    }

    @PostMapping("/inventory/add")
    public String addInventoryPost(@RequestParam("productId") Long productId, @RequestParam("quantity") int numToAdd) {
        productService.addInventoryForOneProduct(productId, numToAdd);

        return "redirect:/inventory";
    }

    @GetMapping("inventory/delete/{id}")
    public String deleteProductGet(@PathVariable("id") Long productId, Model model) {
        model.addAttribute("product", productService.findProductDtoById(productId));

        return "/product/delete-product";
    }

    @PostMapping("inventory/delete")
    public String deleteProductPost(@RequestParam("productId") Long productId) {
        productService.deleteProductById(productId);

        return "redirect:/inventory";
    }

}
