package com.example.controller;

import com.example.pojo.Material;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.pojo.Value;
import com.example.service.MaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/materials")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    /**
     * 物料查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,String spec){
        log.info("分页查询，参数：{},{},{},{}",page,pageSize,name,spec);
        PageBean pageBean = materialService.page(page,pageSize,name,spec);
        return Result.success(pageBean);
    }

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  materialService.searchField(field);
        return Result.success(res);
    }

    /**
     * 删除物料
     * @param material_id
     * @return
     */
    @DeleteMapping("/{material_id}")
    public Result deleteById(@PathVariable Integer material_id){
        log.info("根据id删除物料：{}",material_id);

        materialService.deleteById(material_id);
        return Result.success();
    }

    /**
     * 新增物料类型
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Material material){
        log.info("新增物料:{}",material);
        materialService.insert(material);
        return Result.success();
    }


}
