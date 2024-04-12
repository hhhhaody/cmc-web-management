package com.example.service.impl;

import com.example.mapper.DataMapper;
import com.example.pojo.*;
import com.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

    @Transactional
    @Override
    public void alarm(AlarmData alarmData) {

        //根据设备反馈数据更新报警表alarm_mapping
        dataMapper.setAlarm(alarmData.getAlarmID(), alarmData.getState());

        //暂时使用设备报警数据更新pc端设备报警状态


        dataMapper.alarm(alarmData);
    }

    @Override
    public void material(MaterialData materialData) {
        //更新物料库存表
        dataMapper.material(materialData);
    }

    @Override
    public void product(ProductData productData) {
        dataMapper.product(productData);
    }

    @Override
    @Transactional
    public void state(StateData stateData) {
        //根据设备反馈数据更新设备映射表device_mapping
        String state = dataMapper.getStateInfo(stateData.getStateID());
        dataMapper.setState(stateData.getStationID(),state);

        //处理报警与检修维护逻辑判断
        String status = dataMapper.getStatusInfo(stateData.getStationID());
        String statusNew;

        if(status.equals("报警")){
            dataMapper.setStatus(stateData.getStationID(),"报警");
        } else if (status.equals("检修维护")) {
            dataMapper.setStatus(stateData.getStationID(),"检修维护");
        } else{
            switch(state) {
                case "手动运行":
                case "自动运行":
                case "暂停":
                    statusNew = "正常运行";
                    break;
                case "报警":
                    statusNew = "报警";
                    break;
                case "停止":
                    statusNew = "停机";
                    break;
                default:
                    statusNew = "检修维护";
            }
            dataMapper.setStatus(stateData.getStationID(), statusNew);
        }

        dataMapper.state(stateData);
    }

    @Override
    public void timeConsumed(TimeConsumedData timeConsumedData) {
        dataMapper.timeConsumed(timeConsumedData);
    }

    @Override
    public void energy(EnergyData energyData) {
        dataMapper.energy(energyData);
    }

    @Override
    public List<DeviceMapping> getDevice() {
        return dataMapper.getDevice();
    }

    /**
     * 设备状态由报警改为检修维护
     * @param id
     */
    @Override
    public void setStatus(Long id) {
        //更改设备状态为检修维护
        dataMapper.setStatus(id,"检修维护");

    }
}
