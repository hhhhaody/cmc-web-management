package com.example.service.impl;

import com.example.mapper.ProductionLineMapper;
import com.example.pojo.PageBean;
import com.example.pojo.ProductionLine;
import com.example.pojo.Value;
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

    @Transactional
    @Override
    public void updateStations(ProductionLine productionLine) {
        productionLineMapper.deleteBySection(productionLine.getSection());
        insert(productionLine);
    }
}
