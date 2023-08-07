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
}
