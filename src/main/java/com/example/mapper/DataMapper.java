package com.example.mapper;

import com.example.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Mapper
public interface DataMapper {
    @Insert("insert into alarm_data(sectionID,alarmID,state) values (#{sectionID}, #{alarmID}, #{state})")
    void alarm(AlarmData alarmData);

    @Insert("insert into material_data(sectionID,stationID,productID,materialID,amount) values" +
            " (#{sectionID}, #{stationID}, #{productID},#{materialID},#{amount})")
    void material(MaterialData materialData);

    @Insert("insert into product_data(sectionID,stationID,productID,intermediatesID,amount) values" +
            " (#{sectionID}, #{stationID}, #{productID},#{intermediatesID},#{amount})")
    void product(ProductData productData);

    @Insert("insert into state_data(sectionID,stationID,stateID) values" +
            " (#{sectionID}, #{stationID}, #{stateID})")
    void state(StateData stateData);

    @Insert("insert into time_consumed_data(sectionID,stationID,productID,currentNo,timeConsumed) values" +
            " (#{sectionID}, #{stationID}, #{productID},#{currentNo},#{timeConsumed})")
    void timeConsumed(TimeConsumedData timeConsumedData);

    @Insert("insert into energy_data(sectionID,power,powerConsumption) values" +
            " (#{sectionID}, #{power}, #{powerConsumption})")
    void energy(EnergyData energyData);


    @Update("update alarm_mapping set state = #{state} where id = #{alarmID}")
    void setAlarm(Long alarmID, boolean state);

    //此处station id为设备编码
    @Update("update device_mapping set state = #{state} where id = #{stationID}")
    void setState(Long stationID, String state);

    @Select("select state from state_mapping where id = #{stateID}")
    String getStateInfo(Long stateID);

    @Select("select d.id, d.name, s.sectionName as section, state, status from device_mapping d JOIN section_mapping s ON d.section = s.id where status != '停用'")
    List<DeviceMapping> getDevice();

    //此处station id为设备编码
    @Update("update device_mapping set status = #{status} where id = #{stationID}")
    void setStatus(Long stationID, String status);

    //此处station id为设备编码
    @Select("select status from device_mapping where id = #{stateID}")
    String getStatusInfo(Long stateID);

    @Select("select * from alarm_mapping where deviceId = #{id} and state = true")
    List<AlarmMapping> getAlarmInfo(Long id);


    @Select("select id from section_mapping where sectionName = #{section}")
    Integer getSectionId(String section);

    @Select("select id from state_mapping where state = #{state} and section = #{sectionId}")
    Integer getStateId(String state, Integer sectionId);

    @Select("select max(updateTime) from state_data where stationID = #{id} and stateID = #{stateId}")
    LocalDateTime getLatestStateTime(Long id, Integer stateId);

    @Select("select sectionName from section_mapping where id = #{sectionID}")
    String getSectionName(Long sectionID);

    @Select("select * from material where ${section} != 0 order by name")
    List<Material> getMaterialUsage(String section);

    @Select("select * from product where ${section} != 0 order by name")
    List<Product> getProductAmount(String section);


    List<EnergyRecord> getEnergy(Integer section, LocalDate date);

    List<EnergyRecord> listEnergyDates(String section, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Insert("insert into production_item (item_code, item_name, item_model, production_date) VALUES (#{batchStr},#{name},#{spec},#{now});")
    void insertProductionItem(String batchStr, String name, String spec, LocalDate now);

    @Select("select item_id from production_item where item_code = #{batchStr}")
    Integer getItemIdByItemCode(String batchStr);

    @Select("select time_spent from production_time where item_id = #{itemId} and production_line_id = #{productionLineId}")
    Long getTimeSpent(Integer itemId, Integer productionLineId);

    @Update("update production_time set time_spent = #{time} where item_id = #{itemId} and production_line_id = #{productionLineId}")
    void setTimeSpent(Integer itemId, Integer productionLineId, Long time);

    @Insert("insert into production_time (item_id,production_line_id,time_spent) values (#{itemId},#{productionLineId},#{time})")
    void postTimeSpent(Integer itemId, Integer productionLineId, Long time);


    ProductionDetailDto getTimeConsumed(String section);

    ProductionDetailDto getTheoreticalTime(Integer productId);

    @Insert("insert into message (msg) values (#{msg})")
    void info(String msg);

    List<Message> getMessage(LocalDate date);

    @Select("select * from facility join alarm_mapping on facility.mappingId = alarm_mapping.deviceId where alarm_mapping.id = #{alarmID};")
    Facility getDeviceByAlarmId(Long alarmID);
}
