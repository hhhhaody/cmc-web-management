package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInspection {
    private BigInteger id;
    private String name;
    private String result;
    private LocalDateTime updateTime;
}
