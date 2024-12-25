package com.example.controller;

import com.example.pojo.*;
import com.example.service.DataService;
import com.example.service.TroubleshootingRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/material")
    public Result getMaterialUsage(String section){
        log.info("根据工段原材料使用情况查询，参数：{}",section);
        List<Material> mateirals = dataService.getMaterialUsage(section);
        return Result.success(mateirals);
    }


    @PostMapping("/product")
    public Result product(@RequestBody ProductData productData){
        log.info("接收产品数量数据：{}", productData);
        dataService.product(productData);
        return Result.success("数据传递成功");
    }

    @GetMapping("/product")
    public Result getProductAmount(String section){
        log.info("根据工段查询产品生产情况，参数：{}",section);
        List<Product> products = dataService.getProductAmount(section);
        return Result.success(products);
    }

    /**
     * 根据库存情况查询已有产品生产情况
     * @param section
     * @return
     */
    @GetMapping("/productInInventory")
    public Result getProductInInventoryAmount(String section){
        log.info("根据工段查询产品生产情况，参数：{}",section);
        List<GraphData> products = dataService.getProductInInventoryAmount(section);
        return Result.success(products);
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

    @Transactional
    @PutMapping("/state/{id}")
    public Result setStatus(@PathVariable Long id){
        log.info("更改设备显示状态为检修维护");
        dataService.setStatus(id);

        //新增检修维护记录
        troubleshootingRecordService.addById(id,"报警");
        return Result.success();
    }

    @PostMapping("/timeConsumed")
    public Result timeConsumed(@RequestBody TimeConsumedData timeConsumedData){
        log.info("接收工位耗时数据：{}", timeConsumedData);
        dataService.timeConsumed(timeConsumedData);
        return Result.success("数据传递成功");
    }

    @GetMapping("/timeConsumed")
    public Result getTimeConsumed(String section){
        log.info("根据工段查耗时情况：{}", section);
        List<ProductionDetailDto> productionDetailDto = dataService.getTimeConsumed(section);
        return Result.success(productionDetailDto);
    }

    @PostMapping("/energy")
    public Result energy(@RequestBody EnergyData energyData){
        log.info("接收能耗数据：{}", energyData);
        dataService.energy(energyData);
        return Result.success("数据传递成功");
    }

    @GetMapping("/energy")
    public Result getEnergy(String section,LocalDate date){
        log.info("根据工段查询能耗情况，参数：{},{}",section,date);
        List<EnergyRecord> energyRecords = dataService.getEnergy(section,date);
        return Result.success(energyRecords);
    }

    /**
     * 物料查询
     * @param page
     * @param pageSize
     * @param section
     * @return
     */
    @GetMapping("/energy/list")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String section,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd){
        log.info("根据工段分页查询能耗情况，参数：{},{},{},{},{}",page,pageSize,section,dateStart,dateEnd);
        PageBean pageBean = dataService.listEnergyDates(page,pageSize,section,dateStart,dateEnd);
        return Result.success(pageBean);
    }

    @GetMapping("/message")
    public Result getMessage(LocalDate date){
        log.info("根据工段查询能耗情况，参数：{}",date);
        List<Message> messages = dataService.getMessage(date);
        return Result.success(messages);
    }

    @PostMapping("/materialInspection")
    public Result materialInspection(@RequestBody MaterialInspection materialInspection){
        log.info("接受来料检测数据：{}", materialInspection);
        dataService.materialInspection(materialInspection);
        return Result.success("数据传递成功");
    }

    @GetMapping("/materialInspection")
    public Result materialInspectionPage(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd){
        log.info("根据工段分页查询来料检测数据，参数：{},{},{},{},{}",page,pageSize,name,dateStart,dateEnd);
        PageBean pageBean = dataService.listMaterialInspection(page,pageSize,name,dateStart,dateEnd);
        return Result.success(pageBean);
    }

}
