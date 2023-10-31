package com.example.service.impl;

import com.example.mapper.FacilityMapper;
import com.example.mapper.FacilityStatusMapper;
import com.example.pojo.Facility;
import com.example.pojo.FacilityStatus;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.FacilityService;
import com.example.utils.PinYinUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    FacilityMapper facilityMapper;

    @Autowired
    FacilityStatusMapper facilityStatusMapper;

    @Override
    public List<Value> searchField(String field) {
        return facilityMapper.searchField(field);
    }

    @Override
    public List<Value> search(Facility facility, String field) {
        String name = facility.getName();
        String section = facility.getSection();
        String spec = facility.getSpec();
        String serialNo = facility.getSerialNo();
        return facilityMapper.search(name,spec,section,serialNo,field);
    }

    @Override
    public void insert(Facility facility) {
        Integer batch = facilityMapper.getMaxBatch(facility.getName());
        if(batch != null){
            facility.setBatch(batch+1);
        }
        else facility.setBatch(1);
        Integer batchSame = facilityMapper.getMaxBatchSame(facility.getName(),facility.getSpec());
        if(batchSame != null){
            facility.setBatchSame(batchSame+1);
        }
        else facility.setBatchSame(1);
        facility.setSerialNo(PinYinUtil.getSerialString(facility.getName(), String.valueOf(facility.getBatch()),String.valueOf(facility.getBatchSame())));
        facility.setStatus("正常使用");
        facilityMapper.insert(facility);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, String supplier) {
        PageHelper.startPage(page,pageSize);

        List<Facility> facilityList = facilityMapper.list(name,spec,section,status,supplier);
        Page<Facility> p = (Page<Facility>) facilityList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public void deleteById(Integer facility_id) {
        facilityMapper.deleteById(facility_id);
    }

    @Override
    public Facility getById(Integer facility_id) {
        return facilityMapper.getById(facility_id);
    }

    @Override
    public void update(Facility facility) {
        facilityMapper.update(facility);
    }

    @Transactional
    @Override
    public void updateStatus(Integer facility_id) {
        Facility facility = facilityMapper.getById(facility_id);
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(facility.getName());
        facilityStatus.setSpec(facility.getSpec());
        facilityStatus.setStation(facility.getStation());
        facilityStatus.setSection(facility.getSection());
        facilityStatus.setSerialNo(facility.getSerialNo());
        facilityStatus.setUpdateTime(LocalDateTime.now());

        if (facility.getStatus().equals("正常使用")){
            facilityStatus.setBeforeStatus("正常使用");
            facilityStatus.setAfterStatus("停用");
            facilityMapper.updateStatus(facility_id,"停用");
        }
        else{
            facilityStatus.setBeforeStatus("停用");
            facilityStatus.setAfterStatus("正常使用");
            facilityMapper.updateStatus(facility_id,"正常使用");
        }

        facilityStatusMapper.insert(facilityStatus);
    }
}
