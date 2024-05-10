package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Material {
    private Integer id;
    private String name;
    private String spec;
    private Integer amount;
    private Integer threshold;
    private Integer batch;
    private Integer usage;
    private Integer outbound;
    private Integer 型钢切割工作站;
    private Integer 地面钢网工作站;
    private Integer 方通组焊工作站;
    private Integer 模块总装工作站;

}
