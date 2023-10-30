package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pojo.ProductionRecord;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductionRecordMapper extends BaseMapper<ProductionRecord> {
//    @Select("SELECT id, name, spec, production_date, production_quantity FROM production_record")
//    List<ProductionRecord> findAll();

    /**
     * 分页查询生产记录，可根据产品名称和规格进行过滤。
     * 返回的记录按照生产日期从近到远排序。
     *
     * @param offset 分页的起始位置
     * @param pageSize 每页的记录数量
     * @param name 产品名称（可选）
     * @param spec 产品规格（可选）
     * @return 分页的生产记录列表
     */
    @Select("SELECT id, name, spec, production_date, production_quantity FROM production_record WHERE (name LIKE CONCAT('%', #{name}, '%') OR #{name} IS NULL) AND (spec LIKE CONCAT('%', #{spec}, '%') OR #{spec} IS NULL) ORDER BY production_date DESC LIMIT #{offset}, #{pageSize}")
    List<ProductionRecord> findPage(@Param("offset") int offset, @Param("pageSize") int pageSize, @Param("name") String name, @Param("spec") String spec);

    @Select("SELECT COUNT(*) FROM production_record")
    Long countAll();

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from production_record order by value")
    List<Value> searchField(String field);

    List<Value> search(String name, String spec,String field);

    /**
     * 查询指定日期范围内的生产记录。
     * 返回的记录按照生产日期从近到远排序。
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 日期范围内的生产记录列表
     */
    @Select("SELECT id, name, spec, production_date, production_quantity FROM production_record WHERE production_date BETWEEN #{startDate} AND #{endDate} ORDER BY production_date DESC")
    List<ProductionRecord> findByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
