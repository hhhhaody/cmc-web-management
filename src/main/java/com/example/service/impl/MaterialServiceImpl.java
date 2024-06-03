package com.example.service.impl;

import com.example.mapper.MaterialMapper;
import com.example.pojo.Material;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.MaterialService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialMapper materialMapper;


    @Override
    public void deleteById(Integer material_id) {
        materialMapper.deleteById(material_id);
    }

    @Override
    public void insert(Material material) {
        if (materialMapper.check(material) == null){
        Integer batch = materialMapper.getMaxBatch(material.getName());
        if(batch != null){
            material.setBatch(batch+1);
        }
        else material.setBatch(1);
        materialMapper.insert(material);
        }
        else{
        throw new RuntimeException();
        }
    }

    @Override
    public PageBean page(Integer page, Integer pageSize,String name, String spec) {
//        Long count = materialMapper.count();
        materialMapper.calAmount();

        PageHelper.startPage(page,pageSize);

//        List<Material> materialList = materialMapper.page((page - 1) * pageSize, pageSize);
        List<Material> materialList = materialMapper.list(name,spec);
        Page<Material> p = (Page<Material>) materialList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return materialMapper.searchField(field);
    }

    @Override
    public Material getById(Integer material_id) {
        return materialMapper.getById(material_id);
    }

    @Override
    public void update(Material material) {
        Material res = materialMapper.check(material);
        if (res == null){
            Integer batch = materialMapper.getMaxBatch(material.getName());
            if(batch != null){
                material.setBatch(batch+1);
            }
            else material.setBatch(1);

            materialMapper.update(material);
        }
        else{
            if(res.getId() == material.getId()){
                materialMapper.update(material);
            }
            else{
                throw new RuntimeException();
            }
        }
    }

    @Override
    public List<Value> search(Material material, String field) {
        String name = material.getName();
        String spec = material.getSpec();
        return materialMapper.search(name,spec,field);
    }
}
