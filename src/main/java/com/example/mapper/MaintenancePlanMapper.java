package com.example.mapper;

import com.example.pojo.Facility;
import com.example.pojo.MaintenancePlan;
import com.example.pojo.TroubleshootingRecord;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MaintenancePlanMapper {
    List<MaintenancePlan> list(String section, String name, String spec, String status, String maintenanceman, LocalDate start, LocalDateTime end);

    @Select("select distinct ${field} as value from maintenance_plan where ${field} is not null order by value")
    List<Value> searchField(String field);

    @Insert("insert into maintenance_plan(name, spec, section,station,serialNo,type,plannedTime,completeTime,maintenanceman,info,status,ongoing) VALUES " +
            "(#{name}, #{spec}, #{section},#{station},#{serialNo}, #{type}, #{plannedTime},#{completeTime},#{maintenanceman}, #{info},#{status},#{ongoing})")
    void insert(MaintenancePlan maintenancePlan);

    @Select("select * from maintenance_plan where id = #{id}")
    MaintenancePlan getById(Integer id);

    @Update("update maintenance_plan set ongoing = true where id = #{id}")
    void updateOngoingStatus(Integer id);

    void update(MaintenancePlan maintenancePlan);

    @Delete("delete from maintenance_plan where name = #{name} and spec = #{spec} and section = #{section} and station = #{station} " +
            "and serialNo = #{serialNo} and type = #{type} and status = '待完成'")
    void delete(MaintenancePlan maintenancePlan);

    @Delete("delete from maintenance_plan where serialNo = #{serialNo} and status = '待完成'")
    void deleteBySerialNo(String serialNo);
}
