package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TroubleshootingRecord {
    private Integer id;
    private String name;
    private String spec;
    private String station;
    private String section;
    private String serialNo;
    private String error;
    private LocalDateTime errorTime;
    private LocalDateTime completeTime;
    private String repairman;
    private String info;
    private String status;
    private LocalDateTime updateTime;
}
