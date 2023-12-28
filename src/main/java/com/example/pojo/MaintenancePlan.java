package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenancePlan {
    private Integer id;
    private String name;
    private String spec;
    private String station;
    private String section;
    private String serialNo;
    private String type;
    private LocalDateTime plannedTime;
    private LocalDateTime completeTime;
    private String maintenanceman;
    private String info;
    private String status;
    private Boolean ongoing;
    private LocalDateTime updateTime;
}
