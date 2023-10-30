package com.example.service.impl;

import com.example.mapper.EnergyConsumptionRecordMapper;
import com.example.pojo.EnergyConsumptionRecord;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.EnergyConsumptionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnergyConsumptionRecordServiceImpl implements EnergyConsumptionRecordService {

    @Autowired
    private EnergyConsumptionRecordMapper mapper;

//    @Override
//    public PageBean getRecordsBySectionAndDateRange(Long sectionId, Date startDate, Date endDate, int page, int pageSize) {
//        int offset = (page - 1) * pageSize;
//        List<EnergyConsumptionRecord> records = mapper.selectBySectionAndDateRange(sectionId, startDate, endDate, offset, pageSize);
//        Long total = mapper.countBySectionAndDateRange(sectionId, startDate, endDate);
//        return new PageBean(total, records);
//    }

    @Override
    public Result addRecord(EnergyConsumptionRecord record) {
        int result = mapper.insert(record);
        return result == 1 ? Result.success() : Result.error("Insertion failed");
    }

    @Override
    public Result updateRecord(EnergyConsumptionRecord record) {
        int result = mapper.update(record);
        return result == 1 ? Result.success() : Result.error("Update failed");
    }

    @Override
    public Result deleteRecord(Long recordId) {
        int result = mapper.delete(recordId);
        return result == 1 ? Result.success() : Result.error("Deletion failed");
    }
}

