package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialData {
    private BigInteger id;
    private Long sectionID;
    private Long stationID;
    private Long productID;
    private Long materialID;
    private Long amount;
}
