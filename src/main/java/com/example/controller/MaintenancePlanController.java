package com.example.controller;

import com.example.pojo.*;
import com.example.service.MaintenancePlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/maintenance")
public class MaintenancePlanController {
    @Autowired
    private MaintenancePlanService maintenancePlanService;

    /**
     * 设备维保记录查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @param section
     * @param status
     * @param maintenanceman
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, String spec, String section, String status,
                       String maintenanceman,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime plannedTime){
        log.info("分页查询，参数：{},{},{},{},{},{},{}",page,pageSize,name,spec,section,status,maintenanceman,plannedTime);
        PageBean pageBean = maintenancePlanService.page(page,pageSize,name,spec,section,status,maintenanceman,plannedTime);
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

        List<Value> res =  maintenancePlanService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param maintenancePlan
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody MaintenancePlan maintenancePlan, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",maintenancePlan,field);

        List<Value> res =  maintenancePlanService.search(maintenancePlan,field);
        return Result.success(res);
    }

    /**
     * 新增设备维保记录
     * @param maintenancePlan
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody MaintenancePlan maintenancePlan){
        log.info("新增设备维保记录：{}",maintenancePlan);
        maintenancePlanService.insert(maintenancePlan);
        return Result.success();
    }

    /**
     * 根据id查询设备维保记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id){
        log.info("根据id查询设备维保记录， id：{}",id);
        MaintenancePlan maintenancePlan = maintenancePlanService.getById(id);
        return Result.success(maintenancePlan);
    }

    /**
     * 根据id更新设备维保记录
     * @param maintenancePlan
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody MaintenancePlan maintenancePlan){
        log.info("更新设备维保记录");
        maintenancePlanService.update(maintenancePlan);
        return Result.success();
    }

    /**
     * 根据id更新设备维保记录状态及设备状态
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Result updateStatus(@PathVariable Integer id){
        log.info("根据id查询设备信息， id：{}",id);
        maintenancePlanService.updateOngoingStatus(id);
        return Result.success();
    }
}
