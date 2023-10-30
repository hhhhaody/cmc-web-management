package com.example.controller;

import com.example.pojo.*;
import com.example.service.ProductionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/productionRecord")
public class ProductionRecordController {

    @Autowired
    private ProductionRecordService productionRecordService;

    // 获取生产记录。可根据日期范围或名称和规格进行筛选，并支持分页。
    @GetMapping
    public Result getProductionRecords(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "spec", required = false) String spec) {

        if (startDate != null && endDate != null) {
            List<ProductionRecord> records = productionRecordService.findByDateRange(startDate, endDate);
            return Result.success(records);
        } else {
            PageBean pageBean = productionRecordService.findPage(page, pageSize, name, spec);
            return Result.success(pageBean);
        }
    }

    // 根据字段名搜索数据，例如产品名称或规格。
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  productionRecordService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param productionRecord
     * @param field
     * @return
     */
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody ProductionRecord productionRecord, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",productionRecord,field);

        List<Value> res =  productionRecordService.search(productionRecord,field);
        return Result.success(res);
    }
}

