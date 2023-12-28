package com.example.controller;

import com.example.pojo.DefectiveOperation;
import com.example.pojo.Result;
import com.example.pojo.Value;
import com.example.service.DefectiveOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/defectives/operation")
public class DefectiveOperationController {
    @Autowired
    DefectiveOperationService defectiveOperationService;

    /**
     * 不良物料库管理记录
     * @param defectiveOperation
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody DefectiveOperation defectiveOperation){
        log.info("新增不良物料操作：{}",defectiveOperation);
        defectiveOperationService.insert(defectiveOperation);
        return Result.success(defectiveOperation.getName() + defectiveOperation.getSpec()+"成功"+defectiveOperation.getOperation()+defectiveOperation.getAmount().toString());
    }

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  defectiveOperationService.searchField(field);
        return Result.success(res);
    }

    /**
     * 根据批次号查询物料操作用于回显
     * @param batch
     * @return
     */
    @GetMapping("/{batch}")
    public Result getByBatch(@PathVariable String batch){
        log.info("根据batch查询已有不良物料操作：{}",batch);
        List<DefectiveOperation> defectiveOperationList = defectiveOperationService.getByBatch(batch);
        return Result.success(defectiveOperationList);
    }
}
