package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMapping {
    private int id;
    private String name;
    private String section;
    private String state;
    private String status;
}
