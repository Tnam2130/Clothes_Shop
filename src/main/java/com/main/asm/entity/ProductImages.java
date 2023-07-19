package com.main.asm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "product_images")
@AllArgsConstructor
@NoArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue
    private Long image_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Products products;

    @Column(name = "image_name")
    private String name;
    @Column(name = "image_url")
    private String url;
}
