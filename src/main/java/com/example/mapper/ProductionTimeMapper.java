package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.ProductionDetailDto;
import com.example.pojo.ProductionTime;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProductionTimeMapper extends BaseMapper<ProductionTime> {

    /**
     * 查询production_item表中某个字段的唯一值。
     * @param field 要查询的字段名
     * @return 唯一值的列表
     */
    @Select("select distinct ${field} as value from production_item order by value")
    List<Value> searchField(@Param("field") String field);

    /**
     * 根据产品名称和型号，查询production_item表中某个字段的唯一值。
     * @param itemName 产品名称
     * @param itemModel 产品型号
     * @param field 要查询的字段名
     * @return 唯一值的列表
     */
    @Select("select distinct ${field} as value from production_item where item_name like CONCAT('%',#{itemName},'%') and item_model like CONCAT('%',#{itemModel},'%') order by value")
    List<Value> search(@Param("itemName") String itemName, @Param("itemModel") String itemModel, @Param("field") String field);

    /**
     * 根据工作区段和日期范围，计算匹配的产品详情记录数。
     * @param section 工作区段
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @return 匹配的记录数
     */
    @Select("SELECT COUNT(DISTINCT pi.item_code) " +
            "FROM production_time pt " +
            "JOIN production_line pl ON pt.production_line_id = pl.id " +
            "JOIN production_item pi ON pt.item_id = pi.item_id " +
            "WHERE pl.section = #{section} " +
            "AND (#{startDate} IS NULL OR pi.production_date >= #{startDate}) " +
            "AND (#{endDate} IS NULL OR pi.production_date <= #{endDate})")
    Long countDetailsBySectionWithDate(
            @Param("section") String section,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    /**
     * 根据工作区段、日期范围、产品名称和型号，查询产品详情记录。
     * 结果按生产日期降序排列，并进行分页。
     * @param section 工作区段
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @param itemName 产品名称
     * @param itemModel 产品型号
     * @param pageSize 每页的记录数
     * @param offset 偏移量
     * @return 产品详情的DTO列表
     */
    @Select("SELECT " +
            "pi.item_code as itemCode, " +
            "pi.item_name as itemName, " +
            "pi.item_model as itemModel, " +
            "pi.production_date as productionDate, " +
            "GROUP_CONCAT(CONCAT(pl.station, ':', pt.time_spent)) as stationInfo " +
            "FROM production_time pt " +
            "JOIN production_line pl ON pt.production_line_id = pl.id " +
            "JOIN production_item pi ON pt.item_id = pi.item_id " +
            "WHERE pl.section = #{section} " +
            "AND (#{itemName} IS NULL OR pi.item_name LIKE CONCAT('%',#{itemName},'%')) " +
            "AND (#{itemModel} IS NULL OR pi.item_model LIKE CONCAT('%',#{itemModel},'%')) " +
            "AND (#{startDate} IS NULL OR pi.production_date >= #{startDate}) " +
            "AND (#{endDate} IS NULL OR pi.production_date <= #{endDate}) " +
            "GROUP BY pi.item_code, pi.item_name, pi.item_model, pi.production_date " +
            "ORDER BY pi.production_date DESC " +
            "LIMIT #{pageSize} OFFSET #{offset}")
    List<ProductionDetailDto> findDetailsBySectionWithDate(
            @Param("section") String section,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("itemName") String itemName,
            @Param("itemModel") String itemModel,
            @Param("pageSize") int pageSize,
            @Param("offset") int offset);

}


