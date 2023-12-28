package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Product;
import com.example.pojo.Value;

import java.util.List;

public interface ProductService {
    void insert(Product product);

    PageBean page(Integer page, Integer pageSize, String name, String spec);

    List<Value> searchField(String field);

    List<Value> search(Product product, String field);

    Product getById(Integer product_id);

    void update(Product product);

    void deleteById(Integer product_id);

}
