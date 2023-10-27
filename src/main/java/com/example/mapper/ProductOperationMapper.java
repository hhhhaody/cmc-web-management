package com.example.mapper;

import com.example.pojo.ProductOperation;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ProductOperationMapper {

    @Insert("insert into product_operation(name, spec, amount,produceTime,quality,operation,operateTime,operator,receipt,batch,detail) values (#{name}, #{spec}, #{amount} ,#{produceTime},#{quality},#{operation},#{operateTime},#{operator},#{receipt},#{batch},#{detail})")
    void insert(ProductOperation productOperation);

    List<ProductOperation> list(String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String quality, String operator);

    @Select("select distinct ${field} as value from product_operation order by value")
    List<Value> searchField(String field);

    List<Value> search(String name, String spec, String batch, String field);

    /**
     * 计算此批次余量
     * @param batch
     * @return
     */
    ProductOperation getByBatch(String batch);

    @Select("select * from product_operation where id = #{id}")
    ProductOperation getById(Integer id);

    @Delete("delete from product_operation where id = #{id}")
    void deleteById(Integer id);

    void update(ProductOperation productOperation);
}
