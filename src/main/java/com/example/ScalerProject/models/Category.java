package com.example.ScalerProject.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {
    private String description;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
    @OneToMany
    private List<Product> featuredProducts;
}
