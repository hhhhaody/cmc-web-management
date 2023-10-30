package com.example.controller;

import com.example.pojo.*;
import com.example.service.ProductionTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/productionTime")
public class ProductionTimeController {

    @Autowired
    private ProductionTimeService productionTimeService;

    // 初始化数据绑定器，用于将日期字符串转换为Date对象。
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    // 获取指定工作区段的生产细节。支持通过物品名称、物品模型和日期范围进行筛选，并支持分页。
    @GetMapping
    public Result getProductionDetailsBySection(@RequestParam String section,
                                                @RequestParam(required = false) String itemName,
                                                @RequestParam(required = false) String itemModel,
                                                @RequestParam(required = false) Date startDate,
                                                @RequestParam(required = false) Date endDate,
                                                @RequestParam(defaultValue = "1") Integer page,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            PageBean data = productionTimeService.getProductionDetailsBySection(section, startDate, endDate, itemName, itemModel, page, pageSize);
            return Result.success(data);
        } catch (Exception e) {
            log.error("Error fetching production details for section: " + section, e);
            return Result.error("Failed to fetch production details.");
        }
    }

    // 根据字段名搜索数据，例如产品名称或规格。
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  productionTimeService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param productionDetailDto
     * @param field
     * @return
     */
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody ProductionDetailDto productionDetailDto, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",productionDetailDto,field);

        List<Value> res =  productionTimeService.search(productionDetailDto,field);
        return Result.success(res);
    }
}


