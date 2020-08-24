package me.jerry.example.webflux.service.impl;

import lombok.RequiredArgsConstructor;
import me.jerry.example.webflux.domain.Product;
import me.jerry.example.webflux.repository.ProductRepository;
import me.jerry.example.webflux.service.ProductService;
import me.jerry.example.webflux.type.ProductCategoryType;
import me.jerry.example.webflux.type.YesNoType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

@Transactional(readOnly = true, transactionManager = "readTransactionManager")
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Mono<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Flux<Product> getProducts(List<Long> ids) {

        return Flux.fromIterable(ids)
                .flatMap(id -> productRepository.findById(id));

        // return productRepository.findAllById(ids);
    }

    @Transactional(transactionManager = "writeTransactionManager")
    @Override
    public Flux<Product> saveExamplesProducts() {
        return Flux.fromStream(LongStream.rangeClosed(1, 10).boxed())
                .map(it -> new Product(null, ProductCategoryType.PROPERTY, 1L,
                        it.toString(), it % 6 + 1, it.toString(), YesNoType.Y,
                        LocalDateTime.now(), LocalDateTime.now()))
                .flatMap(it -> productRepository.findBySupplierIdAndSupplierProductId(it.getSupplierId(), it.getSupplierProductId())
                        .switchIfEmpty(productRepository.save(it)));
    }

}
