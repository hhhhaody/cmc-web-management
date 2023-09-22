package com.example.controller;

import com.example.pojo.PageBean;
import com.example.pojo.Result;
import com.example.service.MonitorCaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class MonitorCaptureController {
    @Autowired
    private MonitorCaptureService monitorCaptureService;

    @GetMapping("/capture")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  // 注意这里更改为 DATE
                       @RequestParam(required = false) LocalDate captureTimeStart,  // 更改为 LocalDate
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  // 注意这里更改为 DATE
                       @RequestParam(required = false) LocalDate captureTimeEnd) {  // 更改为 LocalDate
        PageBean pageBean = monitorCaptureService.page(page, pageSize, captureTimeStart, captureTimeEnd);
        return Result.success(pageBean);
    }

}
