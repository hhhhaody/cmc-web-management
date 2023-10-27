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
}
