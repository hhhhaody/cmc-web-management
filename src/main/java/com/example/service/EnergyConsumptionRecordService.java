package com.example.service;

import com.example.pojo.EnergyConsumptionRecord;
import com.example.pojo.PageBean;
import com.example.pojo.Result;

import java.util.Date;

public interface EnergyConsumptionRecordService {
//    PageBean getRecordsBySectionAndDateRange(Long sectionId, Date startDate, Date endDate, int page, int pageSize);
    Result addRecord(EnergyConsumptionRecord record);
    Result updateRecord(EnergyConsumptionRecord record);
    Result deleteRecord(Long recordId);
}

