package com.example.controller;

import com.example.mapper.EnergyConsumptionRecordMapper;
import com.example.mapper.ProductionLineMapper;
import com.example.pojo.EnergyConsumptionRecord;
import com.example.pojo.IdStrPair;
import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.EnergyConsumptionRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/energyConsumption")
public class EnergyConsumptionRecordController {

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Autowired
    private EnergyConsumptionRecordMapper mapper;

    @Autowired
    private EnergyConsumptionRecordService service;

    @GetMapping("/records")
    public Result getRecordsByStationName(@RequestParam String sectionName,
                                          @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                          @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                          @RequestParam int page,
                                          @RequestParam int pageSize) {
        List<Long> sectionIds = productionLineMapper.getSectionIdsBySectionName(sectionName);

        // 当`sectionIds`为空时，返回空记录
        if (sectionIds.isEmpty()) {
            return Result.error("No matching section IDs found for the given station name.");
        }

        int offset = (page - 1) * pageSize;
        List<EnergyConsumptionRecord> records = mapper.selectBySectionIds(sectionIds, startDate, endDate, offset, pageSize);
        Long total = mapper.countBySectionIds(sectionIds, startDate, endDate);

        // 返回分页后的记录
        return Result.success(new PageBean(total, records));
    }

    @GetMapping("/firstRecords")
    public Result getFirstRecordsByStationName(@RequestParam String sectionName,
                                               @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                               @RequestParam(required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                               @RequestParam int page,
                                               @RequestParam int pageSize) {
        List<Long> sectionId = productionLineMapper.getSectionIdsBySectionName(sectionName);

        // 当sectionId为空时，返回错误信息
        if (sectionId == null) {
            return Result.error("No matching section ID found for the given station name.");
        }

        int offset = (page - 1) * pageSize;
        List<EnergyConsumptionRecord> firstRecords = mapper.selectFirstRecordByDate(sectionId, startDate, endDate, offset, pageSize);
        Long total = mapper.countFirstRecordByDate(sectionId, startDate, endDate);
        return Result.success(new PageBean(total, firstRecords));
    }


    /**
     * 功能描述: 根据收到的电流和电压数据，计算功率和能耗，并存储。
     *
     * 1. 计算功率：
     *    - 公式: P = I × V
     *    - 其中:
     *        P: 功率 (单位: Watts)
     *        I: 电流 (单位: Amperes)
     *        V: 电压 (单位: Volts)
     * 2. 计算能耗或能量:
     *    - 公式: E = P × t
     *    - 其中:
     *        E: 能耗或能量 (单位: Joules)
     *        P: 功率 (单位: Watts)
     *        t: 时间 (单位: Seconds)
     *
     * 实现逻辑:
     *    a. 首先检查数据库中的最后一个记录。
     *    b. 如果数据库为空, 只计算并存储功率, 并暂时不计算能耗。
     *    c. 如果数据库中存在先前的记录, 计算两个时间戳之间的差异 (单位: 秒)。
     *    d. 使用给定的电流和电压来计算功率, 并存储。
     *    e. 使用步骤c中计算的时间差, 以及步骤d中的功率, 来计算能耗, 并存储。
     */
    @PostMapping("/record")
    public Result addRecord(@RequestBody EnergyConsumptionRecord record) {
        // 计算功率: Power (P) = Current (I) x Voltage (V)
        double power = record.getCurrent() * record.getVoltage();
        record.setPower(power);

        // 从数据库获取上一个记录的时间戳
        EnergyConsumptionRecord lastRecord = mapper.getLastRecord();

        if (lastRecord != null) {
            // 计算时间差（单位：秒）
            long timeDifference = Duration.between(lastRecord.getTimestamp(), record.getTimestamp()).getSeconds();

            // 计算能耗: Energy (E) = Power (P) x timeDifference (t)
            double energyConsumed = power * timeDifference;
            record.setEnergyConsumed(energyConsumed);
        } else {
            // 如果没有先前的记录，我们只设置功率，不设置能耗
            record.setEnergyConsumed(null);
        }

        // 将记录保存到数据库
        int result = mapper.insert(record);
        return result == 1 ? Result.success() : Result.error("Insertion failed");
    }

    @PutMapping("/record")
    public Result updateRecord(@RequestBody EnergyConsumptionRecord record) {
        return service.updateRecord(record);
    }

    @DeleteMapping("/record/{recordId}")
    public Result deleteRecord(@PathVariable Long recordId) {
        return service.deleteRecord(recordId);
    }
}

