package com.example.mapper;

import com.example.pojo.MonitorCapture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MonitorCaptureMapper {
    @Select({"<script>",
            "SELECT fileName FROM monitor_capture",
            "<where>",
            "<if test='captureTimeStart != null'>AND capture_time &gt;= #{captureTimeStart}</if>",
            "<if test='captureTimeEnd != null'>AND capture_time &lt;= #{captureTimeEnd}</if>",
            "</where>",
            "</script>"})
    List<MonitorCapture> list(LocalDate captureTimeStart, LocalDate captureTimeEnd);
}
