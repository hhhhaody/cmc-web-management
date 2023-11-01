package com.example.service.impl;

import com.example.mapper.FacilityMapper;
import com.example.mapper.FacilityStatusMapper;
import com.example.mapper.TroubleshootingRecordMapper;
import com.example.pojo.*;
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

    @Autowired
    private FacilityStatusMapper facilityStatusMapper;

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
        //TODO: 看情况
        return null;
    }

    @Transactional
    @Override
    public void insert(TroubleshootingRecord troubleshootingRecord) {

        Facility facility = facilityMapper.getBySerialNo(troubleshootingRecord.getSerialNo());
        //更新故障维修记录表
        troubleshootingRecord.setStation(facility.getStation());
        troubleshootingRecord.setStatus("未完成");
        troubleshootingRecordMapper.insert(troubleshootingRecord);

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(troubleshootingRecord.getName());
        facilityStatus.setSpec(troubleshootingRecord.getSpec());
        facilityStatus.setStation(troubleshootingRecord.getStation());
        facilityStatus.setSection(troubleshootingRecord.getSection());
        facilityStatus.setSerialNo(troubleshootingRecord.getSerialNo());
        facilityStatus.setUpdateTime(troubleshootingRecord.getErrorTime());
        facilityStatus.setBeforeStatus("正常使用");
        facilityStatus.setAfterStatus("检修维护");
        facilityStatusMapper.insert(facilityStatus);

        //更新设备状态
        facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"检修维护");


    }

    @Override
    public TroubleshootingRecord getById(Integer troubleshootingRecord_id) {
        return troubleshootingRecordMapper.getById(troubleshootingRecord_id);
    }

    @Transactional
    @Override
    public void update(TroubleshootingRecord troubleshootingRecord) {
        Facility facility = facilityMapper.getBySerialNo(troubleshootingRecord.getSerialNo());
        //补充故障维修记录表
        troubleshootingRecord.setStation(facility.getStation());

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(troubleshootingRecord.getName());
        facilityStatus.setSpec(troubleshootingRecord.getSpec());
        facilityStatus.setStation(troubleshootingRecord.getStation());
        facilityStatus.setSection(troubleshootingRecord.getSection());
        facilityStatus.setSerialNo(troubleshootingRecord.getSerialNo());
        facilityStatus.setUpdateTime(LocalDateTime.now());
        facilityStatus.setBeforeStatus("检修维护");

        //更新设备状态
        if(troubleshootingRecord.getStatus().equals("完成维修")){
            facilityStatus.setAfterStatus("正常使用");
            facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"正常使用");
        }
        else{
            facilityStatus.setAfterStatus("停用");
            facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"停用");
        }
        //更新设备状态记录表
        facilityStatusMapper.insert(facilityStatus);

        troubleshootingRecordMapper.update(troubleshootingRecord);
    }
}
