package me.jerry.example.webflux.service;

import me.jerry.example.webflux.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {

    Mono<Product> getProduct(Long id);

    Flux<Product> getProducts(List<Long> ids);

    Flux<Product> saveExamplesProducts();

}
