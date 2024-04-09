package com.example.controller;

import com.example.pojo.*;
import com.example.service.DataService;
import com.example.service.TroubleshootingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @Autowired
    private TroubleshootingRecordService troubleshootingRecordService;

    /**
     * @param alarmData
     * @return
     */
    @PostMapping("/alarm")
    public Result alarm(@RequestBody AlarmData alarmData){
        log.info("接收报警数据：{}", alarmData);
        dataService.alarm(alarmData);
        return Result.success("数据传递成功");
    }

    @PostMapping("/material")
    public Result material(@RequestBody MaterialData materialData){
        log.info("接收原料消耗数据：{}", materialData);
        dataService.material(materialData);
        return Result.success("数据传递成功");
    }

    @PostMapping("/product")
    public Result product(@RequestBody ProductData productData){
        log.info("接收产品数量数据：{}", productData);
        dataService.product(productData);
        return Result.success("数据传递成功");
    }

    @PostMapping("/state")
    public Result state(@RequestBody StateData stateData){
        log.info("接收工位状态数据：{}", stateData);
        dataService.state(stateData);
        return Result.success("数据传递成功");
    }

    /**
     * 用于设备状态显示
     * @return
     */
    @GetMapping("/state")
    public Result monitor(){
        log.info("查询工位设备状态数据");
        List<DeviceMapping> deviceMappingList = dataService.getDevice();
        return Result.success(deviceMappingList);
    }

    @PutMapping("/state/{id}")
    public Result setStatus(@PathVariable Long id){
        log.info("更改设备显示状态为检修维护");
        dataService.setStatus(id);

        //新增检修维护记录
        troubleshootingRecordService.addById(id);
        return Result.success();
    }

    @PostMapping("/timeConsumed")
    public Result timeConsumed(@RequestBody TimeConsumedData timeConsumedData){
        log.info("接收工位耗时数据：{}", timeConsumedData);
        dataService.timeConsumed(timeConsumedData);
        return Result.success("数据传递成功");
    }

    @PostMapping("/energy")
    public Result energy(@RequestBody EnergyData energyData){
        log.info("接收能耗数据：{}", energyData);
        dataService.energy(energyData);
        return Result.success("数据传递成功");
    }
}
