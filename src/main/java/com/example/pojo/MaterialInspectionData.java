package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInspectionData {
    private String code;
    private String type;
    private String qualification;
    private String pics; // "[xxx.jpg,sssss.jpg]"
    private LocalDateTime updateTime;
    private Map<String, Detail> details;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Detail {
        private String name;
        private String results; // 多个检测值
        private String tolerance; // 容差
        private String qualification; // 每项的合格情况
    }
}
