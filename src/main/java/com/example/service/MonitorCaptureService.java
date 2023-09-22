package com.example.service;

import com.example.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface MonitorCaptureService {
    PageBean page(Integer page, Integer pageSize, LocalDate captureTimeStart, LocalDate captureTimeEnd);
}
