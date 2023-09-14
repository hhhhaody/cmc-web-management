package com.example.service;

import com.example.pojo.Material;
import com.example.pojo.PageBean;
import com.example.pojo.Value;

import java.util.List;

public interface MaterialService {

    /**
     * 删除物料
     */
    void deleteById(Integer material_id);

    /**
     * 新增物料
     * @param material
     */
    void insert(Material material);

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @return
     */
    PageBean page(Integer page, Integer pageSize,String name,String spec);

    /**
     * 查询此field已有数据
     * @param field
     * @return
     */
    List<Value> searchField(String field);

    /**
     * 根据id查询物料
     * @param material_id
     * @return
     */
    Material getById(Integer material_id);

    /**
     * 更新物料信息
     * @param material
     */
    void update(Material material);

    /**
     * 弹框内搜索联想
     * @param material
     * @param field
     * @return
     */
    List<Value> search(Material material,String field);
}
