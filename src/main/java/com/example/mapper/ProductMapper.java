package com.example.mapper;

import com.example.pojo.Product;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("insert into product(name, spec, threshold,stackThreshold,batch) VALUES (#{name}, #{spec}, #{threshold},#{stackThreshold},#{batch})")
    void insert(Product product);

    /**
     * 获得当前产品类型中最大的流水号以生成新的规格的流水号
     * @param name
     * @return
     */
    @Select("SELECT Max(batch) FROM product where name = #{name}")
    Integer getMaxBatch(String name);

    /**
     * 根据名称和规格型号获取产品独特ID
     * @param name
     * @param spec
     * @return
     */
    @Select("SELECT id FROM product where name = #{name} and spec = #{spec}")
    Integer getId(String name, String spec);

    List<Product> list(String name, String spec);

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from product order by value")
    List<Value> searchField(String field);

    /**
     * 弹框内搜索联想
     * @param name
     * @param spec
     * @param field
     * @return
     */
    List<Value> search(String name, String spec, String field);

    /**
     * 根据id查询产品
     * @param product_id
     * @return
     */
    @Select("select * from product where id = #{product_id}")
    Product getById(Integer product_id);

    /**
     * 更新产品信息
     * @param product
     */
    void update(Product product);

    @Delete("delete from product where id = #{product_id}")
    void deleteById(Integer product_id);

    /**
     * 根据名称和规格型号获得此规格物料的流水号用于生成批次号
     * @param name
     * @param spec
     * @return
     */
    @Select("SELECT batch FROM product where name = #{name} and spec = #{spec}")
    Integer getBatch(String name, String spec);

    void calAmount();
}
