package com.example.ScalerProject.services;

import com.example.ScalerProject.dtos.FakeStoreProductDto;
import com.example.ScalerProject.dtos.FakeStoreProductRequestDto;
import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    RestTemplate restTemplate;
    RedisTemplate<String, Object> redisTemplate;

    FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = new RestTemplate();
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Product getProductById(long id) throws ProductNotFoundException {

        Product productFromCache = (Product) redisTemplate.opsForValue().get(String.valueOf(id));
        if (productFromCache != null) {
            return productFromCache;
        }

        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" +
                id, FakeStoreProductDto.class);

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("No product found with id " + id);
        }
        Product productFromFakeStore =  fakeStoreProductDto.toProduct();
        redisTemplate.opsForValue().set(String.valueOf(id), productFromFakeStore);

        return productFromFakeStore;
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
