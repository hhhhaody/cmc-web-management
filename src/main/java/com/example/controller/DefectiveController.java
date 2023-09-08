package com.example.controller;

import com.example.pojo.*;
import com.example.service.DefectiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/defectives")
public class DefectiveController {
    @Autowired
    DefectiveService defectiveService;

    /**
     * 不良物料列表查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @param supplier
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, String spec, String supplier){
        log.info("分页查询，参数：{},{},{},{},{}",page,pageSize,name,spec,supplier);
        PageBean pageBean = defectiveService.page(page,pageSize,name,spec,supplier);
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

        List<Value> res =  defectiveService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param defective
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody Defective defective, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",defective,field);

        List<Value> res =  defectiveService.search(defective,field);
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
        Defective defective = defectiveService.getByBatch(batch);
        return Result.success(defective);
    }

}
