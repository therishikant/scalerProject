package com.example.ScalerProject.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    String name;
    String description;
    String category;
    String imageUrl;
    double price;
}
