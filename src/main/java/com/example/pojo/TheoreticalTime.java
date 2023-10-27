package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheoreticalTime {
    private Integer id;
    private Integer productId;
    private Integer productionLine;
    private Integer theoreticalTime;
}
