package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionRecord {
    private Long id;
    private String name;
    private String spec;
    private String productionDate;
    private Integer productionQuantity;
}
