package com.example.service.impl;

import com.example.mapper.ProductionRecordMapper;
import com.example.pojo.PageBean;
import com.example.pojo.ProductionRecord;
import com.example.pojo.Value;
import com.example.service.ProductionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductionRecordServiceImpl implements ProductionRecordService {

    @Autowired
    private ProductionRecordMapper productionRecordMapper;

    /**
     * 根据给定的参数返回分页后的生产记录。
     * @param page 当前页码
     * @param pageSize 每页的记录数量
     * @param name 生产记录的名称
     * @param spec 生产记录的规格
     * @return 包含分页信息的PageBean对象
     */
    @Override
    public PageBean findPage(int page, int pageSize, String name, String spec) {
        int offset = (page - 1) * pageSize;
        List<ProductionRecord> data = productionRecordMapper.findPage(offset, pageSize, name, spec);
        Long total = productionRecordMapper.countAll();

        PageBean pageBean = new PageBean();
        pageBean.setTotal(total);
        pageBean.setData(data);
        return pageBean;
    }

    /**
     * 查询生产记录表中指定字段的已有数据。
     * @param field 要查询的字段名称
     * @return 字段的唯一值列表
     */
    @Override
    public List<Value> searchField(String field) {
        return productionRecordMapper.searchField(field);
    }

    /**
     * 根据生产记录实体和字段名搜索匹配的记录。
     * @param productionRecord 生产记录实体
     * @param field 要查询的字段名称
     * @return 匹配的记录列表
     */
    @Override
    public List<Value> search(ProductionRecord productionRecord, String field) {
        String name = productionRecord.getName();
        String spec = productionRecord.getSpec();
        return productionRecordMapper.search(name,spec,field);
    }

    /**
     * 根据给定的日期范围查询匹配的生产记录。
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return 匹配的生产记录列表
     */
    @Override
    public List<ProductionRecord> findByDateRange(String startDate, String endDate) {
        return productionRecordMapper.findByDateRange(startDate, endDate);
    }
}
