package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.ProductionDetailDto;
import com.example.pojo.ProductionRecord;
import com.example.pojo.Value;

import java.util.Date;
import java.util.List;

public interface ProductionTimeService {
    PageBean getProductionDetailsBySection(String section, Date startDate, Date endDate, String itemName, String itemModel, Integer page, Integer pageSize);

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    List<Value> searchField(String field);

    /**
     * 弹框内搜索联想
     * @param productionDetailDto
     * @param field
     * @return
     */
    List<Value> search(ProductionDetailDto productionDetailDto, String field);
}
