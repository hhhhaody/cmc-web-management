package com.example.mapper;

import com.example.pojo.TheoreticalTime;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TheoreticalTimeMapper {
    @Insert("insert into theoretical_time(productId, productionLine, theoreticalTime) VALUES (#{productId}, #{productionLine}, #{theoreticalTime})")
    void insert(TheoreticalTime theoreticalTime);

    @Select("select * from theoretical_time where productId = #{product_id}")
    List<TheoreticalTime> getByProductId(Integer product_id);

    @Update("update theoretical_time set theoreticalTime = #{theoreticalTime} where productId = #{productId} and productionLine = #{productionLine}")
    void update(TheoreticalTime theoreticalTime);

    @Delete("delete from theoretical_time where productId = #{product_id}")
    void deleteByProductId(Integer product_id);
}
