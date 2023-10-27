package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.ProductOperation;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductOperationService {
    String insert(ProductOperation productOperation);

    PageBean page(Integer page, Integer pageSize, String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String quality, String operator);

    List<Value> searchField(String field);

    List<Value> search(ProductOperation productOperation, String field);

    ProductOperation getByBatch(String batch);

    ProductOperation getById(Integer id);

    void deleteById(Integer id);

    void update(ProductOperation productOperation);
}
