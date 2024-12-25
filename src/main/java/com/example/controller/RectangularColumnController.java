package com.example.controller;

import com.example.pojo.RectangularColumn;
import com.example.pojo.Result;
import com.example.service.RectangularColumnService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/rectangular")
public class RectangularColumnController {
    @Autowired
    private RectangularColumnService rectangularColumnService;

    @PostMapping("/add")
    public Result insert(@RequestBody RectangularColumn rectangularColumn) {
        log.info("Received RectangularColumn data: {}", rectangularColumn);
        rectangularColumnService.insert(rectangularColumn);
        return Result.success();
    }
}
