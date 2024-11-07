package com.example.service;

import com.example.pojo.Material;
import com.example.pojo.MaterialQuality;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.util.List;

public interface MaterialQualityService {
    PageBean page(Integer page, Integer pageSize, String name, String spec);

    List<Value> search(MaterialQuality materialQuality, String field);

    void insert(MaterialQuality materialQuality);
}
