package com.example.mapper;

import com.example.pojo.FacilityStatus;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface FacilityStatusMapper {
    @Insert("insert into facility_status(name, spec, station,section,serialNo,updateTime,beforeStatus,afterStatus) VALUES " +
            "(#{name}, #{spec}, #{station},#{section},#{serialNo}, #{updateTime}, #{beforeStatus},#{afterStatus})")
    void insert(FacilityStatus facilityStatus);

    @Select("select distinct ${field} as value from facility_status order by value")
    List<Value> searchField(String field);

    List<FacilityStatus> list(String section, String name, String spec, LocalDateTime updateTimeStart, LocalDateTime updateTimeEnd, String serialNo);

    @Delete("delete from facility_status where updateTime = #{completeTime}")
    void deleteByTime(LocalDateTime completeTime);
}
