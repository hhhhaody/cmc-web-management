package com.example.service;

import com.example.pojo.Defective;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.util.List;

public interface DefectiveService {
    PageBean page(Integer page, Integer pageSize, String name, String spec, String supplier);

    List<Value> searchField(String field);

    List<Value> search(Defective defective, String field);

    Defective getByBatch(String batch);
}
