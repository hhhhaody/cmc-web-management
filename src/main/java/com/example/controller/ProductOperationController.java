package com.example.controller;

import com.example.pojo.*;
import com.example.service.ProductOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products/operation")
public class ProductOperationController {
    @Autowired
    private ProductOperationService productOperationService;

    /**
     * 产品出入库操作
     * @param productOperation
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody ProductOperation productOperation){
        log.info("产品操作：{}",productOperation);
        String batch = productOperationService.insert(productOperation);
        return Result.success(productOperation.getName()+ productOperation.getSpec()+"成功"+productOperation.getOperation()+productOperation.getAmount().toString()+"【"+productOperation.getQuality()+"】");
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String operation, String name, String spec,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime operateTimeStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime operateTimeEnd,
                       String quality, String operator){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{},{}",page,pageSize,operation,name,spec,operateTimeStart,operateTimeEnd,quality,operator);
        PageBean pageBean = productOperationService.page(page,pageSize,operation,name,spec,operateTimeStart,operateTimeEnd,quality,operator);
        return Result.success(pageBean);
    }

    /**
     * 产品操作列表搜索
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  productOperationService.searchField(field);
        return Result.success(res);
    }

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody ProductOperation productOperation, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",productOperation,field);

        List<Value> res =  productOperationService.searchAdvance(productOperation,field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param productOperation
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody ProductOperation productOperation, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",productOperation,field);

        List<Value> res =  productOperationService.search(productOperation,field);
        return Result.success(res);
    }

    /**
     * 根据批次号查询产品操作用于回显
     * @param batch
     * @return
     */
    @GetMapping("/{batch}")
    public Result getByBatch(@PathVariable String batch){
        log.info("根据batch查询已有数据：{}",batch);
        ProductOperation productOperation = productOperationService.getByBatch(batch);
        return Result.success(productOperation);
    }

    /**
     * 根据ID查询产品操作
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询产品操作：{}",id);
        ProductOperation productOperation = productOperationService.getById(id);
        return Result.success(productOperation);
    }

    /**
     * 根据ID删除产品操作
     * @param id
     * @return
     */
    @DeleteMapping("/id/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("根据id删除产品操作：{}",id);

        productOperationService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID更新物料操作
     * @param productOperation
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody ProductOperation productOperation){
        log.info("更新产品操作信息");
        productOperationService.update(productOperation);
        return Result.success("成功修改"+productOperation.getBatch()+"信息");
    }
}
