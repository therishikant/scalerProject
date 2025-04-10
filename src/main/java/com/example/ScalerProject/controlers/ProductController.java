package com.example.ScalerProject.controlers;

import com.example.ScalerProject.dtos.CreateProductRequestDto;
import com.example.ScalerProject.dtos.ErrorDto;
import com.example.ScalerProject.dtos.FakeStoreProductRequestDto;
import com.example.ScalerProject.dtos.ProductResponseDto;
import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Product;
import com.example.ScalerProject.services.FakeStoreProductService;
import com.example.ScalerProject.services.ProductDBService;
import com.example.ScalerProject.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    ProductService productService;
    final String serviceName = "fakeStoreProductService";

    ProductController(@Qualifier(serviceName) ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long id) throws ProductNotFoundException {
        Product product =  productService.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.from(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        List<Product> product = productService.getProducts();
        List<ProductResponseDto> responseDtos = new ArrayList<>();
        for(Product p : product){
            ProductResponseDto productResponseDto = ProductResponseDto.from(p);
            responseDtos.add(productResponseDto);
        }
        return new ResponseEntity<>(responseDtos, HttpStatus.ACCEPTED);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody CreateProductRequestDto requestDto){
         Product product = productService.createProduct(
                 requestDto.getName(),
                 requestDto.getDescription(),
                 requestDto.getPrice(),
                 requestDto.getImageUrl(),
                 requestDto.getCategory()
         );
         return new ResponseEntity<>(ProductResponseDto.from(product), HttpStatus.CREATED);
    }

    
}
