package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceFileOperation {
    private Long operationId;
    private String operationType;
    private String itemType;
    private Long itemId;
    private Date performedAt;
}
