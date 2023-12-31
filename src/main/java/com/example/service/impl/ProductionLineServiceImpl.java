package com.example.service.impl;

import com.example.mapper.ProductionLineMapper;
import com.example.pojo.*;
import com.example.service.ProductionLineService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductionLineServiceImpl implements ProductionLineService {
    @Autowired
    ProductionLineMapper productionLineMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        List<ProductionLine> productionLineList = productionLineMapper.list();
        Page<ProductionLine> p = (Page<ProductionLine>) productionLineList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }


    @Transactional
    @Override
    public void insert(ProductionLine productionLine) {
        for (String station : productionLine.getStations()) {
            ProductionLine existingLine = productionLineMapper.findBySectionAndStation(productionLine.getSection(), station);

            if (existingLine == null) {
                // 如果不存在相同记录，则插入新记录
                productionLineMapper.insert(productionLine.getSection(),station);
            }
        }
    }

    @Override
    public void deleteBySection(String section) {
        productionLineMapper.deleteBySection(section);
    }

    @Override
    public List<Value> getBySection(String section) {
        return productionLineMapper.getBySection(section);
    }

    @Override
    public void updateSectionName(String section, String newName) {
        productionLineMapper.updateSectionName(section,newName);
    }

    @Override
    public List<Long> getSectionIdsBySectionName(String sectionName) {
        return productionLineMapper.getSectionIdsBySectionName(sectionName);
    }

    @Transactional
    @Override
    public void updateStations(ProductionLine productionLine) {
        productionLineMapper.deleteBySection(productionLine.getSection());
        insert(productionLine);
    }

    @Override
    public List<Value> searchField(String field) {
        return productionLineMapper.searchField(field);
    }

    @Override
    public List<IdStrPair> getStationsIds(String section) {
        return productionLineMapper.getStationsIds(section);
    }

    @Override
    public List<Value> search(Facility facility, String field) {
        String section = facility.getSection();
        String station = facility.getStation();
        return productionLineMapper.search(section,station,field);
    }
}
