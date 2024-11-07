package com.example.controller;

import com.example.pojo.*;
import com.example.service.MaterialQualityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/materialQuality")
public class MaterialQualityController {
    @Autowired
    MaterialQualityService materialQualityService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, String spec){
        log.info("分页查询，参数：{},{},{},{}",page,pageSize,name,spec);
        PageBean pageBean = materialQualityService.page(page,pageSize,name,spec);
        return Result.success(pageBean);
    }

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody MaterialQuality materialQuality, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",materialQuality,field);

        List<Value> res =  materialQualityService.search(materialQuality,field);
        return Result.success(res);
    }

    @PostMapping
    public Result insert(@RequestBody MaterialQuality materialQuality){
        log.info("新增来料检测数据:{}",materialQuality);
        try{
            materialQualityService.insert(materialQuality);
        }
        catch (Exception e){
            return Result.error("登记失败");
        }
        return Result.success("新增成功");
    }
}
