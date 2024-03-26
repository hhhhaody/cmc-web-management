package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface CarbonService {
    PageBean page(Integer page, Integer pageSize, String type, String section, LocalDateTime startDate, LocalDateTime endDate);

    List<Value> searchField(String field);
}
