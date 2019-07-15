package com.gmail.kramarenko104.services;

import com.gmail.kramarenko104.repositories.ProductRepoImpl;
import com.gmail.kramarenko104.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepoImpl productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepoImpl productRepo){
        this.productRepo = productRepo;
    }

    @Override
    public long createProduct(Product product) {
        return productRepo.createProduct(product);
    }

    @Override
    public Product getProduct(long productId) {
        return productRepo.get(productId);
    }

    @Override
    public void deleteProduct(long productId) {
        productRepo.delete(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.getAll();
    }

    @Override
    public List<Product> getProductsByCategory(int category) {
        return productRepo.getProductsByCategory(category);
    }

}
