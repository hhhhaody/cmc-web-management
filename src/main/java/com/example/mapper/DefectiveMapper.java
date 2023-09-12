package com.example.mapper;

import com.example.pojo.Defective;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DefectiveMapper {
    @Insert("insert into defective(batch, name, spec ,defectiveAmount,supplier,createTime) VALUES (#{batch},#{name}, #{spec}, #{defectiveAmount},#{supplier},#{createTime})")
    void insert(Defective defective);

    List<Defective> list(String name, String spec, String supplier);

    @Select("select distinct ${field} as value from defective order by value")
    List<Value> searchField(String field);

    @Delete("delete from defective where batch = #{batch}")
    void deleteByBatch(String batch);

    List<Value> search(String name, String spec, String batch, String field);

    @Select("select * from defective where batch = #{batch}")
    Defective getByBatch(String batch);

    @Update("update defective set ${field} = #{amount} where batch = #{batch}")
    void updateByBatch(String batch, String field, Integer amount);

    void calAmount();
}
