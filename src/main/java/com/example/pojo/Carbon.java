package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carbon {
    private BigInteger id;
    private String type;
    private String section;
    private float emissions;
    private LocalDateTime updateTime;
}
