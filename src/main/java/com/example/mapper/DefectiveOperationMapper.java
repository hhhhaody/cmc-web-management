package com.example.mapper;

import com.example.pojo.DefectiveOperation;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DefectiveOperationMapper {
    @Select("select distinct ${field} as value from defective_operation order by value")
    List<Value> searchField(String field);

    @Insert("insert into defective_operation(batch,name, spec, amount,operation,operateTime,operator,handler,comment,receipt) values (#{batch},#{name}, #{spec}, #{amount} ,#{operation},#{operateTime},#{operator},#{handler},#{comment},#{receipt})")
    void insert(DefectiveOperation defectiveOperation);

    @Select("select * from defective_operation where batch = #{batch}")
    List<DefectiveOperation> getByBatch(String batch);
}
