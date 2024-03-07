package com.example.service.impl;

import com.example.mapper.DataMapper;
import com.example.pojo.*;
import com.example.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

    @Override
    public void alarm(AlarmData alarmData) {
        dataMapper.alarm(alarmData);
    }

    @Override
    public void material(MaterialData materialData) {
        dataMapper.material(materialData);
    }

    @Override
    public void product(ProductData productData) {
        dataMapper.product(productData);
    }

    @Override
    public void state(StateData stateData) {
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
}
