package com.example.service;

import com.example.pojo.MaterialOperation;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface MaterialOperationService {
    String insert(MaterialOperation materialOperation);

    PageBean page(Integer page, Integer pageSize, String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String supplier, String operator);

    List<Value> searchField(String field);

    List<Value> search(MaterialOperation materialOperation, String field);

    MaterialOperation getByBatch(String batch);

    MaterialOperation getById(Integer id);

    void deleteById(Integer id);
}
