package com.vyatsu.task15.controllers;

import com.vyatsu.task15.entities.Product;
import com.vyatsu.task15.repositories.ProductRepository;
import com.vyatsu.task15.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.vyatsu.task15.repositories.ProductSpecs.*;


@Controller
public class ProductsController {
    private ProductsService productsService;
    private ProductRepository productRepository;

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String Firste()
    {
        return "redirect:/products?Min=&Max=&Substring=";
    }

    String currentString;

    @GetMapping("/products")
    public String ShowProducts(@RequestParam(value = "Min",required = false) Integer Min,
                               @RequestParam(value = "Max",required = false) Integer Max,
                               @RequestParam(value = "Substring",required = false) String Substring,
                               Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable)
    {

        String minStr = Min == null ? "" : Min.toString();
        String maxStr = Max == null ? "" : Max.toString();

        if (Min == null){
            Min = 0;
        }
        if (Max == null) {
            Max = Integer.MAX_VALUE;
        }
        if (Substring == null) {
            Substring = "";
        }

        Page<Product> productPage = productRepository.findAll(titleContainsWord(Substring)
                                                     .and(priceGreaterThanOrEq(Min))
                                                     .and(priceLesserThanOrEq(Max)),
                                                      pageable
        );
        model.addAttribute("products", productPage);

        Integer[] totalPages = new Integer[productPage.getTotalPages()];
        for (int i = 0; i < totalPages.length; i++) {
            totalPages[i] = i + 1;
        }
        model.addAttribute("totalPages", totalPages);

        Product product = new Product();
        model.addAttribute("product", product);

        model.addAttribute("Min", Min);
        model.addAttribute("Max", Max);
        model.addAttribute("Substring", Substring);

        currentString = "?Min=" + minStr + "&Max=" + maxStr + "&Substring=" + Substring;
        return "products";
    }

    @GetMapping("/add")
    public String addProduct(@RequestParam(value = "Name") String Name,
                             @RequestParam(value = "Price") Integer Price)
    {
        productsService.add(Name,Price);
        return "redirect:/products" + currentString;
    }

//    @GetMapping("/show/{id}")
//    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
//        Product product = productsService.getById(id);
//        model.addAttribute("product", product);
//        return "product-page";
//    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam(value = "id") int id)
    {
        productsService.deleteById(id);
        return "redirect:/products" + currentString;
    }

    @GetMapping("/change")
    public String changeProduct(@RequestParam(value = "ID") Integer id,
                                @RequestParam(value = "Name",required = false) String Name,
                                @RequestParam(value = "Price",required = false) Integer Price)
    {
        productsService.changeById(id, Name, Price);
        return "redirect:/products" + currentString;
    }
}
