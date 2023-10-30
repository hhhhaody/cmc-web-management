package com.example.service.impl;

import com.example.mapper.ProductionTimeMapper;
import com.example.pojo.PageBean;
import com.example.pojo.ProductionDetailDto;
import com.example.pojo.Value;
import com.example.service.ProductionTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductionTimeServiceImpl implements ProductionTimeService {

    @Autowired
    private ProductionTimeMapper productionTimeMapper;

    /**
     * 根据指定的部分和其他条件获取生产细节，并进行分页处理。
     * @param section 工位
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param itemName 产品名称
     * @param itemModel 规格型号
     * @param page 当前页码
     * @param pageSize 每页的记录数量
     * @return 包含分页信息和生产细节的PageBean对象
     */
    @Override
    public PageBean getProductionDetailsBySection(String section, Date startDate, Date endDate, String itemName, String itemModel, Integer page, Integer pageSize) {
        List<ProductionDetailDto> details;
        if (startDate != null && endDate != null) {
            details = productionTimeMapper.findDetailsBySectionWithDate(section, startDate, endDate, itemName, itemModel, pageSize, (page-1) * pageSize);
        } else {
            details = productionTimeMapper.findDetailsBySectionWithDate(section, null, null, itemName, itemModel, pageSize, (page-1) * pageSize);
        }

        // 处理 stationInfo 并将其转换为 stationTimes 列表
        for (ProductionDetailDto detail : details) {
            String stationInfo = detail.getStationInfo();
            List<ProductionDetailDto.StationTime> stationTimes = new ArrayList<>();
            for (String info : stationInfo.split(",")) {
                String[] parts = info.split(":");
                stationTimes.add(new ProductionDetailDto.StationTime(parts[0], Integer.parseInt(parts[1])));
            }
            detail.setStationTimes(stationTimes);
        }

        Long total = getTotalCount(section, startDate, endDate);

        PageBean pageBean = new PageBean();
        pageBean.setData(details);
        pageBean.setTotal(total);
        return pageBean;
    }

    /**
     * 查询生产耗时表中指定字段的已有数据。
     * @param field 要查询的字段名称
     * @return 字段的唯一值列表
     */
    @Override
    public List<Value> searchField(String field) {
        return productionTimeMapper.searchField(camelToSnake(field));
    }

    /**
     * 根据生产细节DTO和字段名搜索匹配的记录。
     * @param productionDetailDto 生产细节DTO
     * @param field 要查询的字段名称
     * @return 匹配的记录列表
     */
    @Override
    public List<Value> search(ProductionDetailDto productionDetailDto, String field) {
        String itemName = productionDetailDto.getItemName();
        String itemModel = productionDetailDto.getItemModel();
        return productionTimeMapper.search(itemName, itemModel, camelToSnake(field));
    }

    /**
     * 根据指定的部分和日期范围获取匹配的生产细节记录总数。
     * @param section 生产部分
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 匹配的记录总数
     */
    private Long getTotalCount(String section, Date startDate, Date endDate) {
        return productionTimeMapper.countDetailsBySectionWithDate(section, startDate, endDate);
    }

    // 驼峰命名到下划线命名的转换方法
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
