package com.example.controller;

import com.example.pojo.*;
import com.example.service.TroubleshootingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/troubleshooting")
public class TroubleshootingRecordController {
    @Autowired
    private TroubleshootingRecordService troubleshootingRecordService;

    /**
     * 故障维修记录查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @param section
     * @param status
     * @param completeTimeStart
     * @param completeTimeEnd
     * @param repairman
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, String spec, String section, String status,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime completeTimeStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime completeTimeEnd,
                       String repairman){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{},{}",page,pageSize,name,spec,section,status,completeTimeStart,completeTimeEnd,repairman);
        PageBean pageBean = troubleshootingRecordService.page(page,pageSize,name,spec,section,status,completeTimeStart,completeTimeEnd,repairman);
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

        List<Value> res =  troubleshootingRecordService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param troubleshootingRecord
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody TroubleshootingRecord troubleshootingRecord, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",troubleshootingRecord,field);

        List<Value> res =  troubleshootingRecordService.search(troubleshootingRecord,field);
        return Result.success(res);
    }

    /**
     * 新增故障维修记录
     * @param troubleshootingRecord
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody TroubleshootingRecord troubleshootingRecord){
        log.info("故障维修记录：{}",troubleshootingRecord);
        troubleshootingRecordService.insert(troubleshootingRecord);
        return Result.success();
    }

    /**
     * 根据id查询故障维修记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询故障维修记录， id：{}",id);
        TroubleshootingRecord troubleshootingRecord = troubleshootingRecordService.getById(id);
        return Result.success(troubleshootingRecord);
    }

    /**
     * 根据id更新故障维修记录
     * @param troubleshootingRecord
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody TroubleshootingRecord troubleshootingRecord){
        log.info("更新故障维修记录");
        troubleshootingRecordService.update(troubleshootingRecord);
        return Result.success();
    }
}
