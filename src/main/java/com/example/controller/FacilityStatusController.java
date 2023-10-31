package com.example.controller;

import com.example.pojo.*;
import com.example.service.FacilityStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/facilities/status")
public class FacilityStatusController {
    @Autowired
    private FacilityStatusService facilityStatusService;

    /**
     * 搜索框联想查询
     * @param field
     * @return
     */
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field){
        log.info("根据field查询已有数据：{}",field);

        List<Value> res =  facilityStatusService.searchField(field);
        return Result.success(res);
    }

    /**
     * 弹框内搜索联想
     * @param facilityStatus
     * @param field
     * @return
     */

    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody FacilityStatus facilityStatus, @PathVariable String field){
        log.info("根据用户已输入信息查询已有数据：{},{}",facilityStatus,field);

        List<Value> res =  facilityStatusService.search(facilityStatus,field);
        return Result.success(res);
    }

    /**
     * 设备状态查询
     * @param page
     * @param pageSize
     * @param name
     * @param spec
     * @param section
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     */
    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, String spec, String section,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeStart, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateTimeEnd,
                       String serialNo){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{}",page,pageSize,name,spec,section,updateTimeStart,updateTimeEnd,serialNo);
        PageBean pageBean = facilityStatusService.page(page,pageSize,name,spec,section,updateTimeStart,updateTimeEnd,serialNo);
        return Result.success(pageBean);
    }
}
