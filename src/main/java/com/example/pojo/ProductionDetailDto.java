package com.example.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDetailDto {
    private String itemCode;
    private String itemName;
    private String itemModel;
    private String productionDate;
    private List<StationTime> stationTimes;
    private String stationInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StationTime {
        private String stationName;
        private int timeSpent;
    }
}

