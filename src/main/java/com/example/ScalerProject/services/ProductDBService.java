package com.example.ScalerProject.services;

import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productDBService")
public class ProductDBService implements ProductService {
    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        return null;
    }
}
