package com.example.mapper;

import com.example.pojo.EnergyRecord;
import com.example.pojo.Material;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface MaterialMapper {

    /**
     * 查询物料
     * @return
     */
    List<Material> list(String name, String spec);

    /**
     * 根据id删除物料
     * @param material_id
     */
    @Delete("delete from material where id = #{material_id}")
    void deleteById(Integer material_id);

    /**
     * 新增物料
     * @param material
     */
    @Insert("insert into material(name, spec, threshold,batch) VALUES (#{name}, #{spec}, #{threshold},#{batch})")
    void insert(Material material);

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from material order by value")
    List<Value> searchField(String field);

    /**
     * 根据id查询物料
     * @param material_id
     * @return
     */
    @Select("select * from material where id = #{material_id}")
    Material getById(Integer material_id);

    /**
     * 更新物料信息
     * @param material
     */
    void update(Material material);

    /**
     * 计算库存量
     */
    void calAmount();


    List<Value> search(String name, String spec,String field);

    /**
     * 获得当前物料类型中最大的流水号以生成新的规格的流水号
     * @param name
     * @return
     */
    @Select("SELECT Max(batch) FROM material where name = #{name}")
    Integer getMaxBatch(String name);

    /**
     * 根据名称和规格型号获得此规格物料的流水号用于生成批次号
     * @param name
     * @param spec
     * @return
     */
    @Select("SELECT batch FROM material where name = #{name} and spec = #{spec}")
    Integer getBatch(String name, String spec);

    /**
     * 检查当前物料是否已存在
     * @param material
     */
    @Select("select * from material where name=#{name} and spec = #{spec}")
    Material check(Material material);

    @Update("update material set `${field}` =#{amount} where id = #{id}")
    void setUsage(Integer id,String field, Long amount);

    @Select("select `${field}` from material where id = #{id}")
    Integer getUsage(Integer id, String field);

}
