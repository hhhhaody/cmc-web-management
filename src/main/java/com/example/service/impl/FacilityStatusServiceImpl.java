package com.example.service.impl;

import com.example.mapper.FacilityStatusMapper;
import com.example.pojo.FacilityStatus;
import com.example.pojo.MaterialOperation;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.FacilityStatusService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacilityStatusServiceImpl implements FacilityStatusService {

    @Autowired
    private FacilityStatusMapper facilityStatusMapper;

    @Override
    public List<Value> searchField(String field) {
        return facilityStatusMapper.searchField(field);
    }

    @Override
    public List<Value> search(FacilityStatus facilityStatus, String field) {
        //TODO:
        return null;
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String section, LocalDateTime updateTimeStart, LocalDateTime updateTimeEnd, String serialNo) {
        PageHelper.startPage(page, pageSize);

        List<FacilityStatus> facilityStatusList = facilityStatusMapper.list(section, name, spec, updateTimeStart, updateTimeEnd, serialNo);
        Page<FacilityStatus> p = (Page<FacilityStatus>) facilityStatusList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }
}
