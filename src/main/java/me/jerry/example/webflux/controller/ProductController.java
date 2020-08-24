package me.jerry.example.webflux.controller;

import lombok.RequiredArgsConstructor;
import me.jerry.example.webflux.domain.Product;
import me.jerry.example.webflux.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{id}")
    public Mono<Product> product(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/products/byIds")
    public Flux<Product> product(@RequestParam List<Long> ids) {
        return productService.getProducts(ids);
    }

    @GetMapping("/products/saveExample")
    public Flux<Product> saveExampleProducts() {
        return productService.saveExamplesProducts();
    }

}
