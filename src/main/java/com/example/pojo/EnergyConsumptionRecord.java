package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyConsumptionRecord {
    private Long recordId;
    private Long sectionId;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date productionDate;

    private LocalDateTime timestamp;
    private Double current;
    private Double voltage;
    private Double power;
    private Double energyConsumed;
}

