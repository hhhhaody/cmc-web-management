package com.example.service.impl;

import com.example.mapper.DataMapper;
import com.example.mapper.FacilityMapper;
import com.example.mapper.FacilityStatusMapper;
import com.example.mapper.TroubleshootingRecordMapper;
import com.example.pojo.*;
import com.example.service.TroubleshootingRecordService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TroubleshootingRecordServiceImpl implements TroubleshootingRecordService {

    @Autowired
    private TroubleshootingRecordMapper troubleshootingRecordMapper;

    @Autowired
    private FacilityMapper facilityMapper;

    @Autowired
    private FacilityStatusMapper facilityStatusMapper;

    @Autowired
    private DataMapper dataMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String section, String status, LocalDateTime completeTimeStart, LocalDateTime completeTimeEnd, String repairman) {
        PageHelper.startPage(page, pageSize);

        List<TroubleshootingRecord> troubleshootingRecordList = troubleshootingRecordMapper.list(section, name, spec, status, completeTimeStart, completeTimeEnd, repairman);
        Page<TroubleshootingRecord> p = (Page<TroubleshootingRecord>) troubleshootingRecordList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return troubleshootingRecordMapper.searchField(field);
    }

    @Override
    public List<Value> search(TroubleshootingRecord troubleshootingRecord, String field) {
        //TODO: 看情况
        return null;
    }

    @Transactional
    @Override
    public void insert(TroubleshootingRecord troubleshootingRecord) {

        Facility facility = facilityMapper.getBySerialNo(troubleshootingRecord.getSerialNo());
        //更新故障维修记录表
        troubleshootingRecord.setStation(facility.getStation());
        troubleshootingRecord.setStatus("未完成");
        troubleshootingRecordMapper.insert(troubleshootingRecord);

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(troubleshootingRecord.getName());
        facilityStatus.setSpec(troubleshootingRecord.getSpec());
        facilityStatus.setStation(troubleshootingRecord.getStation());
        facilityStatus.setSection(troubleshootingRecord.getSection());
        facilityStatus.setSerialNo(troubleshootingRecord.getSerialNo());
        facilityStatus.setUpdateTime(troubleshootingRecord.getErrorTime());
        facilityStatus.setBeforeStatus("正常使用");
        facilityStatus.setAfterStatus("检修维护");
        facilityStatusMapper.insert(facilityStatus);

        //更新设备状态
        facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"检修维护");


    }

    @Override
    public TroubleshootingRecord getById(Integer troubleshootingRecord_id) {
        return troubleshootingRecordMapper.getById(troubleshootingRecord_id);
    }

    @Transactional
    @Override
    public void update(TroubleshootingRecord troubleshootingRecord) {
        Facility facility = facilityMapper.getBySerialNo(troubleshootingRecord.getSerialNo());
        //补充故障维修记录表
        troubleshootingRecord.setStation(facility.getStation());

        //检查是否是首次更新
        if(!facility.getStatus().equals("检修维护")){
//            System.out.println(troubleshootingRecord.getUpdateTime());
            facilityStatusMapper.deleteByTime(troubleshootingRecord.getUpdateTime());
        }
        LocalDateTime now = LocalDateTime.now();

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(troubleshootingRecord.getName());
        facilityStatus.setSpec(troubleshootingRecord.getSpec());
        facilityStatus.setStation(troubleshootingRecord.getStation());
        facilityStatus.setSection(troubleshootingRecord.getSection());
        facilityStatus.setSerialNo(troubleshootingRecord.getSerialNo());
        facilityStatus.setUpdateTime(now);
        facilityStatus.setBeforeStatus("检修维护");

        //更新设备状态
        if(troubleshootingRecord.getStatus().equals("完成维修")){
            facilityStatus.setAfterStatus("正常使用");
            facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"正常使用");
            dataMapper.setStatus(facility.getMappingId(),"正常使用");
        }
        else{
            facilityStatus.setAfterStatus("停用");
            facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"停用");
            dataMapper.setStatus(facility.getMappingId(),"停用");
        }
        //更新设备状态记录表
        facilityStatusMapper.insert(facilityStatus);


        troubleshootingRecord.setUpdateTime(now);
        troubleshootingRecordMapper.update(troubleshootingRecord);

    }

    //根据设备映射表id新增设备维护记录
    @Override
    @Transactional
    public void addById(Long id, String state) {
        //新增故障维修记录表
        //根据mappingId读取设备信息
        Facility facility = facilityMapper.getByMappingId(id);
        TroubleshootingRecord troubleshootingRecord = new TroubleshootingRecord();
        troubleshootingRecord.setName(facility.getName());
        troubleshootingRecord.setSpec(facility.getSpec());
        troubleshootingRecord.setSection(facility.getSection());
        troubleshootingRecord.setStation(facility.getStation());
        troubleshootingRecord.setSerialNo(facility.getSerialNo());

        String error = "";
        //查询此设备具体报警信息
        List <AlarmMapping> alarmMappings =  dataMapper.getAlarmInfo(id);
        for (int i = 0; i < alarmMappings.size(); i++) {
            error = error + alarmMappings.get(i).getAlarmInfo();
        }
        if(error.equals("")){
            error = "设备未反馈报警信息";
        }

        troubleshootingRecord.setError(error);
        //根据状态更新时间设定报警时间
        //查询工段编号
        Integer sectionId =  dataMapper.getSectionId(facility.getSection());

        //根据工段编号以及状态信息查询状态编号

        Integer stateId = dataMapper.getStateId(state,sectionId);

        //查询最近一次此状态发生时间
        troubleshootingRecord.setErrorTime(dataMapper.getLatestStateTime(id,stateId));


        troubleshootingRecord.setStatus("未完成");
        troubleshootingRecordMapper.insert(troubleshootingRecord);

        //更新设备状态记录表
        FacilityStatus facilityStatus = new FacilityStatus();
        facilityStatus.setName(troubleshootingRecord.getName());
        facilityStatus.setSpec(troubleshootingRecord.getSpec());
        facilityStatus.setStation(troubleshootingRecord.getStation());
        facilityStatus.setSection(troubleshootingRecord.getSection());
        facilityStatus.setSerialNo(troubleshootingRecord.getSerialNo());
        facilityStatus.setUpdateTime(troubleshootingRecord.getErrorTime());
        facilityStatus.setBeforeStatus("正常使用");
        facilityStatus.setAfterStatus("检修维护");
        facilityStatusMapper.insert(facilityStatus);

        //更新设备状态
        facilityMapper.updateStatusBySerialNo(troubleshootingRecord.getSerialNo(),"检修维护");

    }
}
