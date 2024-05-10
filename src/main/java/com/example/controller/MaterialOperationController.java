package com.example.controller;

import com.example.pojo.*;
import com.example.service.MaterialOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/materials/operation")
public class MaterialOperationController {

    @Autowired
    private MaterialOperationService materialOperationService;

    /**
     * 物料出入库转库操作
     * @param materialOperation
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody MaterialOperation materialOperation){
        log.info("物料操作：{}",materialOperation);
        String batch = materialOperationService.insert(materialOperation);
        return Result.success(materialOperation.getName()+materialOperation.getSpec()+"成功"+materialOperation.getOperation()+"【"+ materialOperation.getAmount().toString()+"】");
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String operation, String name, String spec,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime operateTimeStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime operateTimeEnd,
                       String supplier, String operator){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{},{}",page,pageSize,operation,name,spec,operateTimeStart,operateTimeEnd,supplier,operator);
        PageBean pageBean = materialOperationService.page(page,pageSize,operation,name,spec,operateTimeStart,operateTimeEnd,supplier,operator);
        return Result.success(pageBean);
    }

    /**
     * 物料操作列表搜索
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  materialOperationService.searchField(field);
        return Result.success(res);
    }

    @PostMapping("/searchAdvance/{field}")
    public Result searchAdvanceSuggestion(@RequestBody MaterialOperation materialOperation, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",materialOperation,field);

        List<Value> res =  materialOperationService.searchAdvance(materialOperation,field);
        return Result.success(res);
    }


    /**
     * 弹框内搜索联想
     * @param materialOperation
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody MaterialOperation materialOperation, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",materialOperation,field);

        List<Value> res =  materialOperationService.search(materialOperation,field);
        return Result.success(res);
    }

    /**
     * 根据批次号查询物料操作用于回显
     * @param batch
     * @return
     */
    @GetMapping("/{batch}")
    public Result getByBatch(@PathVariable String batch){
        log.info("根据batch查询已有数据：{}",batch);
        MaterialOperation materialOperation = materialOperationService.getByBatch(batch);
        return Result.success(materialOperation);
    }

    /**
     * 根据ID查询物料操作
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据ID查询物料操作：{}",id);
        MaterialOperation materialOperation = materialOperationService.getById(id);
        return Result.success(materialOperation);
    }

    /**
     * 根据ID删除物料操作
     * @param id
     * @return
     */
    @DeleteMapping("/id/{id}")
    public Result deleteById(@PathVariable Integer id){
        log.info("根据id删除物料操作：{}",id);

        materialOperationService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID更新物料操作
     * @param materialOperation
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody MaterialOperation materialOperation){
        log.info("更新物料操作信息");
        materialOperationService.update(materialOperation);
        return Result.success("成功修改"+materialOperation.getBatch()+"信息");
    }
}
