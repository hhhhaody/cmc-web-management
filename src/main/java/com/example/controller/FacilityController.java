package com.example.controller;

import com.example.pojo.*;
import com.example.service.FacilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/facilities")
public class FacilityController {
    @Autowired
    FacilityService facilityService;

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  facilityService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param facility
     * @param field
     * @return
     */
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody Facility facility, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",facility,field);

        List<Value> res =  facilityService.search(facility,field);
        return Result.success(res);
    }

    /**
     * 新增设备
     * @return
     */
    @PostMapping
    public Result insert(@RequestBody Facility facility){
        log.info("新增设备:{}",facility);
        facilityService.insert(facility);
        return Result.success("新增成功");
    }

    /**
     * 设备查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @param section
     * @param status
     * @param supplier
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,String spec, String section, String status, String supplier, Boolean dailyMaintenance){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{}",page,pageSize,name,spec,section,status,supplier,dailyMaintenance);
        PageBean pageBean = facilityService.page(page,pageSize,name,spec,section,status,supplier,dailyMaintenance);
        return Result.success(pageBean);
    }

    /**
     * 删除设备
     * @param facility_id
     * @return
     */
    @DeleteMapping("/{facility_id}")
    public Result deleteById(@PathVariable Integer facility_id){
        log.info("根据id删除设备：{}",facility_id);

        facilityService.deleteById(facility_id);
        return Result.success();
    }

    /**
     * 根据id查询设备信息
     * @param facility_id
     * @return
     */
    @GetMapping("/{facility_id}")
    public Result getById(@PathVariable Integer facility_id){
        log.info("根据id查询设备信息， id：{}",facility_id);
        Facility facility = facilityService.getById(facility_id);
        return Result.success(facility);
    }

    /**
     * 根据id更新设备状态
     * @param facility_id
     * @return
     */
    @PutMapping("/{facility_id}")
    public Result updateStatus(@PathVariable Integer facility_id){
        log.info("根据id查询设备信息， id：{}",facility_id);
        facilityService.updateStatus(facility_id);
        return Result.success();
    }

    /**
     * 根据id更新设备信息
     * @param facility
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody Facility facility){
        log.info("更新设备信息");
        facilityService.update(facility);
        return Result.success("更新成功");
    }

    /**
     * 根据serialNo更新设备前一次日常维护日期
     * @param serialNo
     * @return
     */
    @PutMapping("/daily/{serialNo}")
    public Result updateDailyTime(@PathVariable String serialNo){
        log.info("更新设备日常维护时间");
        facilityService.updateDailyTime(serialNo);
        return Result.success();
    }

}
