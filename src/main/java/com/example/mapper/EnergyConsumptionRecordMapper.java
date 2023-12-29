package com.example.mapper;

import com.example.pojo.EnergyConsumptionRecord;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface EnergyConsumptionRecordMapper {
//    @Select("SELECT * FROM EnergyConsumptionRecord WHERE section_id = #{sectionId} AND production_date BETWEEN #{startDate} AND #{endDate} ORDER BY production_date DESC, timestamp DESC LIMIT #{offset}, #{pageSize}")
//    List<EnergyConsumptionRecord> selectBySectionAndDateRange(Long sectionId, Date startDate, Date endDate, int offset, int pageSize);

//    @Select("SELECT COUNT(*) FROM EnergyConsumptionRecord WHERE section_id = #{sectionId} AND production_date BETWEEN #{startDate} AND #{endDate}")
//    Long countBySectionAndDateRange(Long sectionId, Date startDate, Date endDate);

    @Insert("INSERT INTO EnergyConsumptionRecord (section_id, production_date, timestamp, current, voltage, power, energy_consumed) VALUES (#{sectionId}, #{productionDate}, #{timestamp}, #{current}, #{voltage}, #{power}, #{energyConsumed})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    int insert(EnergyConsumptionRecord record);

    @Update("UPDATE EnergyConsumptionRecord SET section_id = #{sectionId}, production_date = #{productionDate}, timestamp = #{timestamp}, current = #{current}, voltage = #{voltage}, power = #{power}, energy_consumed = #{energyConsumed} WHERE record_id = #{recordId}")
    int update(EnergyConsumptionRecord record);

    @Delete("DELETE FROM EnergyConsumptionRecord WHERE record_id = #{recordId}")
    int delete(Long recordId);

    @Select("<script>" +
            "SELECT * FROM energyconsumptionrecord " +
            "WHERE section_id IN " +
            "<foreach item='item' index='index' collection='sectionIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "<if test='startDate != null and endDate != null'> " +
            "AND production_date BETWEEN #{startDate} AND #{endDate} " +
            "</if> " +
            "ORDER BY production_date DESC, timestamp DESC " +
            "LIMIT #{offset}, #{pageSize} " +
            "</script>")
    List<EnergyConsumptionRecord> selectBySectionIds(@Param("sectionIds") List<Long> sectionIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("<script>" +
            "SELECT * FROM EnergyConsumptionRecord WHERE (section_id, production_date, timestamp) IN (" +
            "SELECT section_id, production_date, MIN(timestamp) " +
            "FROM EnergyConsumptionRecord " +
            "WHERE section_id IN " +
            "<foreach item='item' index='index' collection='sectionId' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "<if test='startDate != null and endDate != null'> " +
            "AND production_date BETWEEN #{startDate} AND #{endDate} " +
            "</if> " +
            "GROUP BY section_id, production_date) " +
            "ORDER BY production_date DESC, timestamp ASC " +
            "LIMIT #{offset}, #{pageSize} " +
            "</script>")
    List<EnergyConsumptionRecord> selectFirstRecordByDate(@Param("sectionId") List<Long> sectionId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("offset") int offset, @Param("pageSize") int pageSize);

    @Select("<script>" +
            "SELECT COUNT(DISTINCT production_date) FROM EnergyConsumptionRecord " +
            "WHERE section_id IN " +
            "<foreach item='item' index='index' collection='sectionIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach> " +
            "<if test='startDate != null and endDate != null'> " +
            "AND production_date BETWEEN #{startDate} AND #{endDate} " +
            "</if> " +
            "</script>")
    Long countFirstRecordByDate(@Param("sectionIds") List<Long> sectionIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("<script>" +
            "select count(*) from energyconsumptionrecord where section_id in " +
            "<foreach item='item' index='index' collection='sectionIds' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>" +
            "<if test='startDate != null and endDate != null'>" +
            "AND production_date BETWEEN #{startDate} AND #{endDate} " + // 添加的日期范围条件
            "</if>" +
            "</script>")
    Long countBySectionIds(@Param("sectionIds") List<Long> sectionIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Select("SELECT * FROM EnergyConsumptionRecord ORDER BY timestamp DESC LIMIT 1")
    EnergyConsumptionRecord getLastRecord();
}

