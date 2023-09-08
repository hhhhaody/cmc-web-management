package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefectiveOperation {
    private Integer id;
    private String batch;
    private String name;
    private String spec;
    private Integer amount;
    private String operation;
    private LocalDateTime operateTime;
    private String operator;
    private String handler;
    private String comment;
    private String receipt;
}
