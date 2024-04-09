package com.example.service;

import com.example.pojo.*;

import java.util.List;

public interface DataService {
    void alarm(AlarmData alarmData);

    void material(MaterialData materialData);

    void product(ProductData productData);

    void state(StateData stateData);

    void timeConsumed(TimeConsumedData timeConsumedData);

    void energy(EnergyData energyData);

    List<DeviceMapping> getDevice();

    void setStatus(Long id);
}
