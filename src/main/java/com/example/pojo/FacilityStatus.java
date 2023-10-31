package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityStatus {
    private Integer id;
    private String name;
    private String spec;
    private String station;
    private String section;
    private String serialNo;
    private LocalDateTime updateTime;
    private String beforeStatus;
    private String afterStatus;
}
