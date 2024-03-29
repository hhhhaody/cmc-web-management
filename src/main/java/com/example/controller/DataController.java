package com.example.controller;

import com.example.pojo.*;
import com.example.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

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
