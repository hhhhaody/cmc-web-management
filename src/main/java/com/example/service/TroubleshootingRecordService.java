package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.TroubleshootingRecord;
import com.example.pojo.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface TroubleshootingRecordService {
    PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, LocalDateTime completeTimeStart, LocalDateTime completeTimeEnd, String repairman);

    List<Value> searchField(String field);

    List<Value> search(TroubleshootingRecord troubleshootingRecord, String field);

    void insert(TroubleshootingRecord troubleshootingRecord);

    TroubleshootingRecord getById(Integer troubleshootingRecord_id);

    void update(TroubleshootingRecord troubleshootingRecord);

    void addById(Long id);
}
