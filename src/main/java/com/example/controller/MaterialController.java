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
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  materialService.searchField(field);
        return Result.success(res);
    }

    /**
     * 列表搜索联想
     * @param material
     * @param field
     * @return
     */

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody Material material,@PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",material,field);

        List<Value> res =  materialService.search(material,field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param material
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody Material material,@PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",material,field);

        List<Value> res =  materialService.search(material,field);
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
        return Result.success("新增成功");
    }

    /**
     * 根据id查询物料信息
     * @param material_id
     * @return
     */
    @GetMapping("/{material_id}")
    public Result getById(@PathVariable Integer material_id){
        log.info("根据id查询物料信息， id：{}",material_id);
        Material material = materialService.getById(material_id);
        return Result.success(material);
    }

    /**
     * 根据id更新物料信息
     * @param material
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody Material material){
        log.info("更新物料信息");
        materialService.update(material);
        return Result.success("更新成功");
    }
}
