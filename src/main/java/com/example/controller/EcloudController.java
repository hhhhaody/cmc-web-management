package com.example.controller;

import com.example.pojo.Result;
import com.example.pojo.Sts;
import com.example.service.EcloudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/ecloud")
public class EcloudController {

    @Autowired
    private EcloudService ecloudService;

    @GetMapping
    public Result getSts(){
        log.info("Getting Sts credentials");
        Sts sts = ecloudService.getSts();
        return Result.success(sts);
    }

}
