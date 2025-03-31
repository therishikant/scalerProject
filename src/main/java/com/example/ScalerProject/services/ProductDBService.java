package com.example.ScalerProject.services;

import com.example.ScalerProject.exceptions.ProductNotFoundException;
import com.example.ScalerProject.models.Category;
import com.example.ScalerProject.models.Product;
import com.example.ScalerProject.repositories.CategoryRepository;
import com.example.ScalerProject.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDBService")
public class ProductDBService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;

    ProductDBService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String description, double price, String imageUrl, String category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDB(category);

        product.setCategory(categoryObj);
        return productRepository.save(product);
    }

    private Category getCategoryFromDB(String category) {
        Optional<Category> optionalCategory = categoryRepository.findByName(category);
        if(optionalCategory.isPresent()){
            return optionalCategory.get();
        }
        Category categoryObj = new Category();
        categoryObj.setName(category);
        return categoryRepository.save(categoryObj);
    }
}
