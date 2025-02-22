package com.example.ScalerProject.controlers;

import com.example.ScalerProject.dtos.ProductResponseDto;
import com.example.ScalerProject.models.Product;
import com.example.ScalerProject.services.FakeStoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    FakeStoreProductService fakeStoreProductService;

    ProductController(FakeStoreProductService fakeStoreProductService) {
        this.fakeStoreProductService = fakeStoreProductService;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable long id){
        Product product =  fakeStoreProductService.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.from(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.ACCEPTED);
    }
}
