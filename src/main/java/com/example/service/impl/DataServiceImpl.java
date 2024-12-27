package com.example.service.impl;

import com.example.mapper.*;
import com.example.pojo.*;
import com.example.service.DataService;
import com.example.service.TroubleshootingRecordService;
import com.example.utils.PinYinUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private MaterialMapper materialMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private ProductionLineMapper productionLineMapper;

    @Autowired
    private TroubleshootingRecordService troubleshootingRecordService;

    @Transactional
    @Override
    public void alarm(AlarmData alarmData) {

        try{
            //根据设备反馈数据更新报警表alarm_mapping
            dataMapper.setAlarm(alarmData.getAlarmID(), alarmData.getState());

            Facility facility = dataMapper.getDeviceByAlarmId(alarmData.getAlarmID());
            if(facility.getName() == "控制系统"){
            }

            dataMapper.alarm(alarmData);
        } catch (Exception e) {
            System.out.println("请检查或更新映射文件与编号");

            dataMapper.alarm(alarmData);
        }

    }

    @Transactional
    @Override
    public void material(MaterialData materialData) {

        try{
            //更新物料库存表
            if(materialData.getMaterialID() != 0 && materialData.getProductID() != 0) {
                Material material = materialMapper.getById(Math.toIntExact(materialData.getMaterialID()));

                if (material.getUsage() == null) {
                    materialMapper.setUsage(material.getId(), "usage", materialData.getAmount());
                } else {
                    materialMapper.setUsage(material.getId(), "usage", material.getUsage() + materialData.getAmount());
                }
                String sectionName = dataMapper.getSectionName(materialData.getSectionID());
                Integer section_usage = materialMapper.getUsage(material.getId(), sectionName);
                materialMapper.setUsage(material.getId(), sectionName, section_usage + materialData.getAmount());
            }
            dataMapper.material(materialData);
        } catch (Exception e){
            System.out.println("请检查或更新映射文件与编号");
            dataMapper.material(materialData);
        }
    }

    @Transactional
    @Override
    public void product(ProductData productData) {

        try{
        //更新产品表生产量
        if(productData.getProductID() != 0) {
            String sectionName = dataMapper.getSectionName(productData.getSectionID());
            Integer section_production = productMapper.getProductionAmount(productData.getProductID(), sectionName);
            productMapper.setProductionAmount(productData.getProductID(), sectionName, section_production + productData.getAmount());
        }
        dataMapper.product(productData);
        }
        catch (Exception e){
            System.out.println("请检查或更新映射文件与编号");
            dataMapper.product(productData);
        }
    }

    @Override
    @Transactional
    public void state(StateData stateData) {
        dataMapper.state(stateData);

        try{
            if(stateData.getStateID() != 0) {
                //根据设备反馈数据更新设备映射表device_mapping
                String state = dataMapper.getStateInfo(stateData.getStateID());
                dataMapper.setState(stateData.getStationID(), state);

                //处理报警与检修维护逻辑判断
                String status = dataMapper.getStatusInfo(stateData.getStationID());
                String statusNew;

                if (status.equals("报警")) {
//            dataMapper.setStatus(stateData.getStationID(),"报警");
                } else if (status.equals("检修维护")) {
//            dataMapper.setStatus(stateData.getStationID(),"检修维护");
                } else {
                    switch (state) {
                        case "手动运行":
                        case "自动运行":
                        case "暂停":
                            statusNew = "正常运行";
                            break;
                        case "报警":
                            statusNew = "报警";

                            //推送报警信息到信息中心表
                            Facility facility = facilityMapper.getByMappingId(stateData.getStationID());
                            dataMapper.info("设备:【" + facility.getSection() + "-" + facility.getStation() + "-" + facility.getName() + "】故障报警");
                            break;
                        case "停止":
                            statusNew = "停机";
                            break;
                        default:
                            statusNew = "检修维护";
                            troubleshootingRecordService.addById(stateData.getStationID(),state);
                    }
                    dataMapper.setStatus(stateData.getStationID(), statusNew);

                }

            }
        } catch (Exception e) {
            System.out.println("请检查或更新映射文件与编号");

            dataMapper.state(stateData);
        }


    }

    @Override
    @Transactional
    public void timeConsumed(TimeConsumedData timeConsumedData) {
        try{
        if(timeConsumedData.getProductID() != 0) {
            LocalDate now = LocalDate.now();
            timeConsumedData.setTimeConsumed(timeConsumedData.getTimeConsumed() / 1000);
            Product product = productMapper.getById(Math.toIntExact(timeConsumedData.getProductID()));
            Facility facility = facilityMapper.getByMappingId(timeConsumedData.getStationID());
            String batchStr = PinYinUtil.getBatchStringDate(product.getName(), String.valueOf(product.getBatch()), now.atStartOfDay(), Math.toIntExact(timeConsumedData.getCurrentNo()));

            Integer item_id = dataMapper.getItemIdByItemCode(batchStr);

            //新增production_item
            if (item_id == null) {
                dataMapper.insertProductionItem(batchStr, product.getName(), product.getSpec(), now);

                item_id = dataMapper.getItemIdByItemCode(batchStr);
            }

            //新增production_time
            //查找设备对应工位
            Integer production_line_id = productionLineMapper.findBySectionAndStation(facility.getSection(), facility.getStation()).getId();

            //如果已存在同工位耗时，说明此工位包含多种设备，将耗时累加
            Long time = dataMapper.getTimeSpent(item_id, production_line_id);
            if (time != null) {
                dataMapper.setTimeSpent(item_id, production_line_id, time + timeConsumedData.getTimeConsumed());
            } else {
                //不存在则新增耗时
                dataMapper.postTimeSpent(item_id, production_line_id, timeConsumedData.getTimeConsumed());
            }
        }
        dataMapper.timeConsumed(timeConsumedData);}
        catch (Exception e){
            System.out.println("请检查或更新映射文件与编号");
            dataMapper.timeConsumed(timeConsumedData);
        }
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
     *
     * @param id
     */
    @Override
    public void setStatus(Long id) {
        //更改设备状态为检修维护
        dataMapper.setStatus(id, "检修维护");

    }

    @Override
    public List<Material> getMaterialUsage(String section) {
        return dataMapper.getMaterialUsage(section);
    }

    @Override
    public List<Product> getProductAmount(String section) {
        return dataMapper.getProductAmount(section);
    }

    @Override
    public List<GraphData> getProductInInventoryAmount(String section) {
        return dataMapper.getProductInInventoryAmount(section);
    }

    @Override
    @Transactional
    public List<EnergyRecord> getEnergy(String section, LocalDate date) {
        return dataMapper.getEnergy(dataMapper.getSectionId(section), date);
    }

    @Override
    @Transactional
    public PageBean listEnergyDates(Integer page, Integer pageSize, String section, LocalDateTime dateStart, LocalDateTime dateEnd) {
        PageHelper.startPage(page, pageSize);

        List<EnergyRecord> energyRecords = dataMapper.listEnergyDates(section, dateStart, dateEnd);
        Page<EnergyRecord> p = (Page<EnergyRecord>) energyRecords;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<ProductionDetailDto> getTimeConsumed(String section) {
        ProductionDetailDto productionDetailDto = dataMapper.getTimeConsumed(section);
        Integer productId = productMapper.getId(productionDetailDto.getItemName(), productionDetailDto.getItemModel());
        ProductionDetailDto theoreticalTime = dataMapper.getTheoreticalTime(productId);
        List<ProductionDetailDto> resultList = Arrays.asList(productionDetailDto, theoreticalTime);
        return resultList;
    }

    @Override
    public List<Message> getMessage(LocalDate date) {
        return dataMapper.getMessage(date);
    }

    @Override
    public void materialInspection(MaterialInspection materialInspection) {
        dataMapper.materialInspection(materialInspection);
    }

    @Override
    public PageBean listMaterialInspection(Integer page, Integer pageSize, String name, LocalDateTime dateStart, LocalDateTime dateEnd) {

        PageHelper.startPage(page, pageSize);

        List<MaterialInspection> materialInspections = dataMapper.listMaterialInspection(name, dateStart, dateEnd);
        Page<MaterialInspection> p = (Page<MaterialInspection>) materialInspections;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }



    @Override
    public void inspection(Inspection inspection) {
        dataMapper.insertInspection(inspection);
    }
}
