package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialQuality {
    private Integer id;
    private String name;
    private String spec;
    private String surface;
    private String curFace;
    private String length;
    private String width;
    private String thickness;
    private String flatness;
    private String derust;
    private String rAngle;
    private String crossSection;
    private String inspector;
    private LocalDateTime inspectionTime;
    private String batch;
}
