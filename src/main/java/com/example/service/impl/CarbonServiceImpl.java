package com.example.service.impl;

import com.example.mapper.CarbonMapper;
import com.example.pojo.Carbon;
import com.example.pojo.Defective;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.CarbonService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarbonServiceImpl implements CarbonService {
    @Autowired
    private CarbonMapper carbonMapper;
    @Override
    public PageBean page(Integer page, Integer pageSize, String type,String section, LocalDateTime startDate, LocalDateTime endDate) {
        PageHelper.startPage(page,pageSize);

        List<Carbon> carbonList = carbonMapper.list(type,section,startDate,endDate);
        Page<Carbon> p = (Page<Carbon>) carbonList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return carbonMapper.searchField(field);
    }
}
