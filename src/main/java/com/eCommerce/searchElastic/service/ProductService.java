package com.eCommerce.searchElastic.service;

import com.eCommerce.searchElastic.model.Product;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface ProductService {
    Product add(Product product);
    Product findOne(String productId);
    Iterable<Product> findAll();
    void delete(String productId);
    Product update(Product product);
    Collection<Product> query(String queryText);


    int countFunc(String name);
}
