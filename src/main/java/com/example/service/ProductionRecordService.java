package com.example.service;

import com.example.pojo.PageBean;
import com.example.pojo.ProductionRecord;
import com.example.pojo.Value;

import java.util.List;

public interface ProductionRecordService {

    /**
     * 获取生产记录的分页数据。
     *
     * @param page 当前页码。
     * @param pageSize 每页的记录数量。
     * @param name 生产记录的名称关键字。
     * @param spec 生产记录的规格关键字。
     * @return 包含生产记录列表和分页信息的PageBean对象。
     */
    PageBean findPage(int page, int pageSize, String name, String spec);

    /**
     * 根据给定的字段名称查询在生产记录中的唯一值。
     *
     * @param field 要查询的字段名称。
     * @return 唯一值的列表。
     */
    List<Value> searchField(String field);

    /**
     * 根据用户已输入的信息查询已有数据，并提供搜索建议。
     *
     * @param productionRecord 生产记录实体，包含用户已输入的信息。
     * @param field 要查询的字段名称。
     * @return 唯一值的列表。
     */
    List<Value> search(ProductionRecord productionRecord, String field);

    /**
     * 根据给定的日期范围查询生产记录。
     *
     * @param startDate 开始日期。
     * @param endDate 结束日期。
     * @return 日期范围内的生产记录列表。
     */
    List<ProductionRecord> findByDateRange(String startDate, String endDate);
}
