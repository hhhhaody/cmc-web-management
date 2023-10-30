package com.example.service;

import com.example.pojo.Facility;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.util.List;

public interface FacilityService {
    List<Value> searchField(String field);

    List<Value> search(Facility facility, String field);

    void insert(Facility facility);

    PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, String supplier);

    void deleteById(Integer facility_id);

    Facility getById(Integer facility_id);

    void update(Facility facility);

    void updateStatus(Integer facility_id);
}
