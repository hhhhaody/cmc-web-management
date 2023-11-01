package com.example.service;

import com.example.pojo.MaintenancePlan;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface MaintenancePlanService {
    PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, String maintenanceman, LocalDateTime plannedTime);

    List<Value> searchField(String field);

    List<Value> search(MaintenancePlan maintenancePlan, String field);

    void insert(MaintenancePlan maintenancePlan);

    MaintenancePlan getById(Integer id);

    void update(MaintenancePlan maintenancePlan);

    void updateOngoingStatus(Integer id);
}
