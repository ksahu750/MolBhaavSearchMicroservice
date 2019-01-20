package com.eCommerce.searchElastic.service.impl;

import com.eCommerce.searchElastic.model.Product;
import com.eCommerce.searchElastic.repository.ProductRepository;
import com.eCommerce.searchElastic.service.ProductService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MultiMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.Collection;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findOne(String productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void delete(String productId) {
        productRepository.delete(productId);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Collection<Product> query(String queryString){
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery(queryString)
                        .lenient(true)
                        .field("productName")
                        .field("productUsp")
                        .field("productDescription")
                        .field("staticAttributeList.attributeDescription")
                ).should(
                        QueryBuilders.queryStringQuery("*" + queryString + "*")
                        .lenient(true)
                        .field("productName")
                        .field("productUsp")
                        .field("productDescription")
                        .field("staticAttributeList.attributeDescription")
                );

        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        return elasticsearchTemplate.queryForList(build, Product.class);
    }

    @Override
    public int countFunc(String name) {
        return productRepository.countByProductName(name);
    }
}
