package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AlarmData {
    private BigInteger id;
    private Long sectionID;
    private Long alarmID;
    private Boolean state;
}
