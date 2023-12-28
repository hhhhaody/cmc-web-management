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

    /**
     * 计算此批次余量
     * @param batch
     * @return
     */
    MaterialOperation getByBatch(String batch);

    /**
     * 只查这个批次的数据用于不良物料返用
     * @param batch
     * @return
     */
    @Select("select * from material_operation where batch = #{batch} and operation = '入库'")
    MaterialOperation getByBatchSimple(String batch);

    @Select("select * from material_operation where id = #{id}")
    MaterialOperation getById(Integer id);

    @Delete("delete from material_operation where id = #{id}")
    void deleteById(Integer id);

    void update(MaterialOperation materialOperation);

    /**
     * 计算返用批次总剩余量
     * @param decode
     * @return
     */
    MaterialOperation getByBatchReturned(String decode);

    /**
     * 更新此批次号生产日期
     * @param batch
     * @param supplyTime
     */
    @Update("update material_operation set supplyTime = #{supplyTime} where batch = #{batch} ")
    void updateSupplyTime(String batch, LocalDateTime supplyTime);

    List<Value> searchAdvance(String name, String spec, String operation, String supplier, String operator, String field);
}
