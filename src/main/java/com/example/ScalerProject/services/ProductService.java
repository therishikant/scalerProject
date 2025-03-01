package com.example.ScalerProject.services;

import com.example.ScalerProject.dtos.FakeStoreProductDto;
import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Product;

import java.util.List;

public interface ProductService
{
    Product getProductById(long id) throws ProductNotFoundException;

    List<Product> getProducts();

    Product createProduct(String name, String description, double price,
                          String imageUrl, String category);
}
