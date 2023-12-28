package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facility {
    private Integer id;
    private String name;
    private String spec;
    private String station;
    private String section;
    private String serialNo;
    private LocalDateTime purchaseTime;
    private String supplier;
    private String contact;
    private Integer contactNo;
    private String status;
    private String warranty;
    private Boolean dailyMaintenance;
    private String firstLevelMaintenance;
    private String secondLevelMaintenance;
    private String attachment;
    private Integer batch;
    private Integer batchSame;
    private LocalDateTime prevDailyTime;
    private String prevMaintenanceman;
    private String prevInfo;

}
