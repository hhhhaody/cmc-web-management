package com.example.mapper;

import com.example.pojo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
}
