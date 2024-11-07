package com.example.service.impl;

import com.example.mapper.MaterialQualityMapper;
import com.example.pojo.Material;
import com.example.pojo.MaterialQuality;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.MaterialQualityService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialQualityServiceImpl implements MaterialQualityService {
    @Autowired
    MaterialQualityMapper materialQualityMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec) {
//        materialQualityMapper.calAmount();

        PageHelper.startPage(page,pageSize);

        List<MaterialQuality> materialQualityList = materialQualityMapper.list(name,spec);
        Page<MaterialQuality> p = (Page<MaterialQuality>) materialQualityList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<Value> search(MaterialQuality materialQuality, String field) {
        String name = materialQuality.getName();
        String spec = materialQuality.getSpec();
        return materialQualityMapper.search(name,spec,field);
    }

    @Override
    public void insert(MaterialQuality materialQuality) {
        materialQualityMapper.insert(materialQuality);
    }
}
