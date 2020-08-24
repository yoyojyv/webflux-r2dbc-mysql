package me.jerry.example.webflux.domain;

import lombok.*;
import me.jerry.example.webflux.type.ProductCategoryType;
import me.jerry.example.webflux.type.YesNoType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("product")
public class Product {

    @Id private Long id;
    private ProductCategoryType productCategoryCode;
    private Long supplierId;
    private String supplierProductId;
    private Long mainCategoryId;
    private String name;
    private YesNoType useYn;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
