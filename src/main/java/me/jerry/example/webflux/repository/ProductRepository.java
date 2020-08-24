package me.jerry.example.webflux.repository;

import me.jerry.example.webflux.domain.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    @Query("SELECT * FROM product WHERE supplier_id = :supplierId AND supplier_product_id = :supplierProductId ")
    Mono<Product> findBySupplierIdAndSupplierProductId(Long supplierId, String supplierProductId);

}
