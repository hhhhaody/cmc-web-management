package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateData {
    private BigInteger id;
    private Long sectionID;
    private Long stationID; //实际上为设备id
    private Long stateID;
}
