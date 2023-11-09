package com.example.service.impl;

import com.example.mapper.FacilityMapper;
import com.example.mapper.FacilityStatusMapper;
import com.example.mapper.MaintenancePlanMapper;
import com.example.pojo.*;
import com.example.service.MaintenancePlanService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MaintenancePlanServiceImpl implements MaintenancePlanService {

    @Autowired
    MaintenancePlanMapper maintenancePlanMapper;

    @Autowired
    FacilityMapper facilityMapper;

    @Autowired
    FacilityStatusMapper facilityStatusMapper;

    @Transactional
    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, String maintenanceman, LocalDateTime plannedTime, String calendar) {
        //TODO: 删减相同设备多余一级/二级保养
        PageHelper.startPage(page, pageSize);

        if(plannedTime != null){
            if(calendar != null){
                LocalDate cur = plannedTime.toLocalDate();
                LocalDate start = cur.minusMonths(1);

                LocalDateTime end = cur.plusMonths(1).atTime(LocalTime.of(23, 59, 59));
                List<MaintenancePlan> maintenancePlanList = maintenancePlanMapper.list(section, name, spec, status, maintenanceman,start,end);
                Page<MaintenancePlan> p = (Page<MaintenancePlan>) maintenancePlanList;
                PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
                return pageBean;
            }
            else{
                LocalDate start = plannedTime.toLocalDate();
//            LocalDate end = start.plusDays(1);


                // 设置时间为 23:59:59
//            LocalDateTime end = LocalDateTime.from(start).with(LocalTime.of(23, 59, 59));
                LocalDateTime end = start.atTime(LocalTime.of(23, 59, 59));
                List<MaintenancePlan> maintenancePlanList = maintenancePlanMapper.list(section, name, spec, status, maintenanceman,start,end);
                Page<MaintenancePlan> p = (Page<MaintenancePlan>) maintenancePlanList;
                PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
                return pageBean;
            }

        }
        else{
            List<MaintenancePlan> maintenancePlanList = maintenancePlanMapper.list(section, name, spec, status, maintenanceman,null,null);
            Page<MaintenancePlan> p = (Page<MaintenancePlan>) maintenancePlanList;
            PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
            return pageBean;

        }


    }

    @Override
    public List<Value> searchField(String field) {
        return maintenancePlanMapper.searchField(field);
    }

    @Override
    public List<Value> search(MaintenancePlan maintenancePlan, String field) {
        //TODO: 看情况
        return null;
    }

    @Transactional
    @Override
    public void insert(MaintenancePlan maintenancePlan) {
        Facility facility = facilityMapper.getBySerialNo(maintenancePlan.getSerialNo());
        //更新设备维护记录表
        maintenancePlan.setStation(facility.getStation());
        maintenancePlan.setStatus("待完成");
        maintenancePlan.setOngoing(false);
        maintenancePlanMapper.insert(maintenancePlan);
    }

    @Override
    public MaintenancePlan getById(Integer id) {
        return maintenancePlanMapper.getById(id);
    }

    @Transactional
    @Override
    public void update(MaintenancePlan maintenancePlan) {
        Facility facility = facilityMapper.getBySerialNo(maintenancePlan.getSerialNo());
        //补充故障维修记录表
        maintenancePlan.setStation(facility.getStation());
        LocalDateTime completeTime = maintenancePlan.getCompleteTime();
        LocalDateTime plannedTime = maintenancePlan.getPlannedTime();
        if(completeTime.compareTo(plannedTime)<=0){
            maintenancePlan.setStatus("已完成");
        }
        else{
            maintenancePlan.setStatus("逾期完成");
        }

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(maintenancePlan.getName());
        facilityStatus.setSpec(maintenancePlan.getSpec());
        facilityStatus.setStation(maintenancePlan.getStation());
        facilityStatus.setSection(maintenancePlan.getSection());
        facilityStatus.setSerialNo(maintenancePlan.getSerialNo());
        facilityStatus.setUpdateTime(completeTime);
        facilityStatus.setBeforeStatus("检修维护");
        facilityStatus.setAfterStatus("正常使用");

        //更新设备状态
        facilityMapper.updateStatusBySerialNo(maintenancePlan.getSerialNo(),"正常使用");
        //更新设备状态记录表
        facilityStatusMapper.insert(facilityStatus);

        maintenancePlanMapper.update(maintenancePlan);

        //如果完成的是一级或二级保养，新增下次保养计划
        if(maintenancePlan.getType().equals("一级保养")){
            String firstLevelMaintenance = facility.getFirstLevelMaintenance();
            LocalDateTime start = completeTime;

            Integer first = Integer.parseInt(firstLevelMaintenance.replaceAll("[^0-9]", ""));

            MaintenancePlan maintenancePlan1 = new MaintenancePlan();

            maintenancePlan1.setName(facility.getName());
            maintenancePlan1.setSpec(facility.getSpec());
            maintenancePlan1.setSection(facility.getSection());
            maintenancePlan1.setStation(facility.getStation());
            maintenancePlan1.setSerialNo(facility.getSerialNo());
            maintenancePlan1.setType("一级保养");
            maintenancePlan1.setStatus("待完成");
            maintenancePlan1.setOngoing(false);
            if(firstLevelMaintenance.endsWith("月")){
                maintenancePlan1.setPlannedTime(start.plusMonths(first));
            }
            else{
                maintenancePlan1.setPlannedTime(start.plusYears(first));
            }

            maintenancePlanMapper.insert(maintenancePlan1);
        } else if (maintenancePlan.getType().equals("二级保养")) {
            String secondLevelMaintenance = facility.getSecondLevelMaintenance();
            LocalDateTime start = completeTime;

            Integer second = Integer.parseInt(secondLevelMaintenance.replaceAll("[^0-9]", ""));

            MaintenancePlan maintenancePlan2 = new MaintenancePlan();

            maintenancePlan2.setName(facility.getName());
            maintenancePlan2.setSpec(facility.getSpec());
            maintenancePlan2.setSection(facility.getSection());
            maintenancePlan2.setStation(facility.getStation());
            maintenancePlan2.setSerialNo(facility.getSerialNo());
            maintenancePlan2.setType("二级保养");
            maintenancePlan2.setStatus("待完成");
            maintenancePlan2.setOngoing(false);
            if(secondLevelMaintenance.endsWith("月")){
                maintenancePlan2.setPlannedTime(start.plusMonths(second));
            }
            else{
                maintenancePlan2.setPlannedTime(start.plusYears(second));
            }
            maintenancePlanMapper.insert(maintenancePlan2);
        }
    }

    @Transactional
    @Override
    public String updateOngoingStatus(Integer id) {

        Facility facility = facilityMapper.getBySerialNo(maintenancePlanMapper.getById(id).getSerialNo());
        if(facility.getStatus().equals("检修维护")){
            return "设备已在检修中";
        }

        //更新设备维护记录onging状态
        maintenancePlanMapper.updateOngoingStatus(id);

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(facility.getName());
        facilityStatus.setSpec(facility.getSpec());
        facilityStatus.setStation(facility.getStation());
        facilityStatus.setSection(facility.getSection());
        facilityStatus.setSerialNo(facility.getSerialNo());
        facilityStatus.setUpdateTime(LocalDateTime.now());
        facilityStatus.setBeforeStatus(facility.getStatus());
        facilityStatus.setAfterStatus("检修维护");
        facilityStatusMapper.insert(facilityStatus);

        //更新设备状态
        facilityMapper.updateStatusBySerialNo(maintenancePlanMapper.getById(id).getSerialNo(),"检修维护");
        return "更新成功";
    }
}
