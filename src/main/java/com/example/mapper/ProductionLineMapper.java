package com.example.mapper;

import com.example.pojo.ProductionLine;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductionLineMapper {

    @Select("select distinct section from production_line order by section")
    List<ProductionLine> list();

    @Insert("insert into production_line(section, station) VALUES (#{section}, #{station})")
    void insert(String section, String station);

    /**
     * 查找是否有已存在的组合
     * @param section
     * @param station
     * @return
     */
    @Select("select * from production_line where section = #{section} and station = #{station}")
    ProductionLine findBySectionAndStation(String section, String station);

    @Delete("delete from production_line where section = #{section}")
    void deleteBySection(String section);

    @Select("select station as value from production_line where section = #{section}")
    List<Value> getBySection(String section);

    @Update("update production_line set section = #{newName} where section = #{section}")
    void updateSectionName(String section, String newName);
}
