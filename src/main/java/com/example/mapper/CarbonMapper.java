package com.example.mapper;

import com.example.pojo.Carbon;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CarbonMapper {
    List<Carbon> list(String type, String section, LocalDateTime startDate, LocalDateTime endDate);

    @Select("select distinct ${field} as value from carbon_emissions order by value")
    List<Value> searchField(String field);
}
