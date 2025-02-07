package com.example.service;

import com.example.pojo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

    List<Material> getMaterialUsage(String section);

    List<Product> getProductAmount(String section);

    List<EnergyRecord> getEnergy(String section, LocalDate date);

    PageBean listEnergyDates(Integer page, Integer pageSize, String section, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<ProductionDetailDto> getTimeConsumed(String section);

    List<Message> getMessage(LocalDate date);

    void materialInspection(MaterialInspection materialInspection);

    PageBean listMaterialInspection(Integer page, Integer pageSize, String name, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<GraphData> getProductInInventoryAmount(String section);

    void inspection(Inspection inspection);

    void saveMaterialInspection(MaterialInspectionData materialInspectionData);

    PageBean listMaterialInspections(Integer page, Integer pageSize, String type, LocalDateTime dateStart, LocalDateTime dateEnd);

    List<MaterialInspectionData.Detail> getmaterialInspectionsDetailsByCode(String code);

    List<Value> searchField(String field);
}
