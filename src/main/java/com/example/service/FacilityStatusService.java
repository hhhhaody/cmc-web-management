package com.example.service;

import com.example.pojo.FacilityStatus;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface FacilityStatusService {
    List<Value> searchField(String field);

    List<Value> search(FacilityStatus facilityStatus, String field);

    PageBean page(Integer page, Integer pageSize, String name, String spec, String section, LocalDateTime updateTimeStart, LocalDateTime updateTimeEnd, String serialNo);
}
