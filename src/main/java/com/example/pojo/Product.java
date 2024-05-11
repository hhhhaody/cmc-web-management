package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String spec;
    private Integer qualified;
    private Integer discarded;
    private Integer threshold;
    private Integer stackThreshold;
    private Integer batch;
    private String section;
    private List<Integer> stations;
    private List<Integer> times;
    private Integer 型钢切割工作站;
    private Integer 地面钢网工作站;
    private Integer 方通组焊工作站;
    private Integer 模块总装工作站;
}
