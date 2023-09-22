package com.example.service.impl;

import com.example.mapper.MonitorCaptureMapper;
import com.example.pojo.MonitorCapture;
import com.example.pojo.PageBean;
import com.example.service.MonitorCaptureService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonitorCaptureServiceImpl implements MonitorCaptureService {
    @Autowired
    private MonitorCaptureMapper monitorCaptureMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, LocalDate captureTimeStart, LocalDate captureTimeEnd) {
        PageHelper.startPage(page, pageSize);
        List<MonitorCapture> monitorCaptureList = monitorCaptureMapper.list(captureTimeStart, captureTimeEnd);

        Page<MonitorCapture> p = (Page<MonitorCapture>) monitorCaptureList;
        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }
}
