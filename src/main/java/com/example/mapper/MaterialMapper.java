package com.example.mapper;

import com.example.pojo.Material;
import com.example.pojo.MaterialOperation;
import com.example.pojo.Number;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Mapper
public interface MaterialMapper {
//    /**
//     * 查询总记录数
//     * @return
//     */
//    @Select("select count(*) from material")
//    public Long count();
//
//    /**
//     * 分页查询获取列表数据
//     * @param page
//     * @param pageSize
//     * @return
//     */
//    @Select("select * from material limit #{page},#{pageSize}")
//    public List<Material> page(Integer page, Integer pageSize);


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

    @Update("update material set amount = #{amount} where name = #{name} and spec = #{spec}")
    void stock(String name, String spec, Integer amount);

    void calAmount();

    @Select("select amount from material where name = #{name} and spec = #{spec}")
    Number getAmount(String name, String spec);

    List<Value> search(String name, String spec,String field);

    @Select("SELECT Max(batch) FROM material where name = #{name}")
    Integer getMaxBatch(String name);

    @Select("SELECT batch FROM material where name = #{name} and spec = #{spec}")
    Integer getBatch(String name, String spec);
}
