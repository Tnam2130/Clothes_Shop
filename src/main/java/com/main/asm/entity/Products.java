package com.main.asm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name="product_name")
    private String name;
    private String description;
    private Double price;
    private Double oldPrice;
    private boolean deleted=false;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @JsonIgnore
    // MappedBy trỏ tới tên biến products nằm trong ProductImages
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImages> images;
}
