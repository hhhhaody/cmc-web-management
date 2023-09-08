package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Defective {
    private Integer id;
    private String batch;
    private String name;
    private String spec;
    private Integer defectiveAmount;
    private Integer returnedAmount;
    private Integer scrappedAmount;
    private String supplier;
    private LocalDateTime createTime;
}
