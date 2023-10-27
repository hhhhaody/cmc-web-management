package com.example.service;

import com.example.pojo.IdStrPair;
import com.example.pojo.PageBean;
import com.example.pojo.ProductionLine;
import com.example.pojo.Value;

import java.util.List;

public interface ProductionLineService {
    PageBean page(Integer page, Integer pageSize);


    void insert(ProductionLine productionLine);

    void deleteBySection(String section);

    List<Value> getBySection(String section);

    void updateSectionName(String section, String newName);

    void updateStations(ProductionLine productionLine);

    List<Value> searchField(String field);

    List<IdStrPair> getStationsIds(String section);
}
