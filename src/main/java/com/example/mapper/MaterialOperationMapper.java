package com.example.mapper;


import com.example.pojo.MaterialOperation;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MaterialOperationMapper {

    @Insert("insert into material_operation(name, spec, amount ,supplyTime,supplier,operation,operateTime,operator,receipt,batch) values (#{name}, #{spec}, #{amount} ,#{supplyTime},#{supplier},#{operation},#{operateTime},#{operator},#{receipt},#{batch})")
    void insert(MaterialOperation materialOperation);

    List<MaterialOperation> list(String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String supplier, String operator);

    @Select("select distinct ${field} as value from material_operation order by value")
    List<Value> searchField(String field);

    List<Value> search(String name, String spec, String batch, String field);

    MaterialOperation getByBatch(String batch);

    @Select("select * from material_operation where id = #{id}")
    MaterialOperation getById(Integer id);

    @Delete("delete from material_operation where id = #{id}")
    void deleteById(Integer id);

    @Select("select receipt from material_operation where id = #{id}")
    String findById(Integer id);

    void update(MaterialOperation materialOperation);

    @Update("update material_operation set supplier = #{supplier}, supplyTime = #{supplyTime} where batch = #{batch}")
    void updateByBatch(String batch, String supplier, LocalDateTime supplyTime);
}
