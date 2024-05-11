package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnergyRecord {
    private Integer section;
    private LocalDate date;
    private LocalTime time;
    private float avgPower;
    private float avgConsumption;
}


