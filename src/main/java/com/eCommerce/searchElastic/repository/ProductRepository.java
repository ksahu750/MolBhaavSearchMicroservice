package com.eCommerce.searchElastic.repository;

import com.eCommerce.searchElastic.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    public int countByProductName(String name);
}
