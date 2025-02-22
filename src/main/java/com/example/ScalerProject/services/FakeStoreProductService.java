package com.example.ScalerProject.services;

import com.example.ScalerProject.dtos.FakeStoreProductDto;
import com.example.ScalerProject.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }
    @Override
    public Product getProductById(long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" +
                id, FakeStoreProductDto.class);

        return fakeStoreProductDto.toProduct();
    }
}
