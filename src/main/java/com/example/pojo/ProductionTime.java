package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTime {
    private Long production_time_id;
    private Long item_id;
    private Long production_line_id;
    private Integer time_spent;
}
