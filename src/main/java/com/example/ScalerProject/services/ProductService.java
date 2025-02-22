package com.example.ScalerProject.services;

import com.example.ScalerProject.dtos.FakeStoreProductDto;
import com.example.ScalerProject.models.Product;

public interface ProductService
{
    Product getProductById(long id);
}
