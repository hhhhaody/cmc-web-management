package com.example.mapper;

import com.example.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
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

    @Select("select * from device_mapping where status != '停用'")
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
}
