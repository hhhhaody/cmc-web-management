package com.example.service.impl;

import com.example.mapper.FacilityMapper;
import com.example.mapper.TroubleshootingRecordMapper;
import com.example.pojo.Facility;
import com.example.pojo.PageBean;
import com.example.pojo.TroubleshootingRecord;
import com.example.pojo.Value;
import com.example.service.TroubleshootingRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TroubleshootingRecordServiceImpl implements TroubleshootingRecordService {

    @Autowired
    private TroubleshootingRecordMapper troubleshootingRecordMapper;

    @Autowired
    private FacilityMapper facilityMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, LocalDateTime completeTimeStart, LocalDateTime completeTimeEnd, String repairman) {
        PageHelper.startPage(page, pageSize);

        List<TroubleshootingRecord> troubleshootingRecordList = troubleshootingRecordMapper.list(section, name, spec, status, completeTimeStart, completeTimeEnd, repairman);
        Page<TroubleshootingRecord> p = (Page<TroubleshootingRecord>) troubleshootingRecordList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return troubleshootingRecordMapper.searchField(field);
    }

    @Override
    public List<Value> search(TroubleshootingRecord troubleshootingRecord, String field) {
        //TODO:
        return null;
    }

    @Transactional
    @Override
    public void insert(TroubleshootingRecord troubleshootingRecord) {
        Facility facility = facilityMapper.getBySerialNo(troubleshootingRecord.getSerialNo());
        troubleshootingRecord.setStation(facility.getStation());
        troubleshootingRecord.setStatus("未完成");
        troubleshootingRecordMapper.insert(troubleshootingRecord);
    }
}
