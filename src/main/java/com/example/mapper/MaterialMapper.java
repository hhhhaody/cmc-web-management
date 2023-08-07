package com.example.mapper;

import com.example.pojo.Material;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    @Insert("insert into material(name, spec, threshold) VALUES (#{name}, #{spec}, #{threshold})")
    void insert(Material material);

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from material order by value")
    List<Value> searchField(String field);
}
