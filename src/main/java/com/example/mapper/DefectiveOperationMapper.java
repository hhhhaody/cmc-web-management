package com.example.mapper;

import com.example.pojo.DefectiveOperation;
import com.example.pojo.MaterialOperation;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DefectiveOperationMapper {
    @Select("select distinct ${field} as value from defective_operation order by value")
    List<Value> searchField(String field);

    @Insert("insert into defective_operation(batch,name, spec, amount,operation,operateTime,operator,handler,comment,receipt) values (#{batch},#{name}, #{spec}, #{amount} ,#{operation},#{operateTime},#{operator},#{handler},#{comment},#{receipt})")
    void insert(DefectiveOperation defectiveOperation);

    @Select("select * from defective_operation where batch = #{batch} order by operateTime desc")
    List<DefectiveOperation> getByBatch(String batch);

    @Delete("delete from defective_operation where operateTime = #{operateTime}")
    void deleteByOperateTime(LocalDateTime operateTime);

    void updateByOperateTime(MaterialOperation materialOperation);
}
