package com.example.ScalerProject.services;

import com.example.ScalerProject.dtos.FakeStoreProductDto;
import com.example.ScalerProject.dtos.FakeStoreProductRequestDto;
import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = new RestTemplate();
    }
    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" +
                id, FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("No product found with id " + id);
        }

        return fakeStoreProductDto.toProduct();
    }

    @Override
    public List<Product> getProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = fakeStoreProductDto.toProduct();
            products.add(product);
        }
        return products;
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        
        FakeStoreProductRequestDto requestDto = new FakeStoreProductRequestDto();
        requestDto.setTitle(name);
        requestDto.setDescription(description);
        requestDto.setPrice(price);
        requestDto.setImageUrl(imageUrl);
        requestDto.setCategory(category);

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject("https://fakestoreapi.com/products",
                requestDto,
                FakeStoreProductDto.class);
        return fakeStoreProductDto.toProduct();
    }
}
