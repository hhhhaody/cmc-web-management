package com.example.mapper;

import com.example.pojo.TroubleshootingRecord;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TroubleshootingRecordMapper {
    List<TroubleshootingRecord> list(String section, String name, String spec, String status, LocalDateTime completeTimeStart, LocalDateTime completeTimeEnd, String repairman);

    @Select("select distinct ${field} as value from troubleshooting_record order by value")
    List<Value> searchField(String field);

    @Insert("insert into troubleshooting_record(name, spec, section,station,serialNo,error,errorTime,completeTime,repairman,info,status) VALUES " +
            "(#{name}, #{spec}, #{section},#{station},#{serialNo}, #{error}, #{errorTime},#{completeTime},#{repairman}, #{info},#{status})")
    void insert(TroubleshootingRecord troubleshootingRecord);
}
