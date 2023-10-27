package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOperation {
    private Integer id;
    private String name;
    private String spec;
    private Integer amount;
    private LocalDateTime produceTime;
    private String quality;
    private String operation;
    private LocalDateTime operateTime;
    private String operator;
    private String receipt;
    private String detail;
    private String batch;
}
