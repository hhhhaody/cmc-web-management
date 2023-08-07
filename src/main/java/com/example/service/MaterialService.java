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
}
