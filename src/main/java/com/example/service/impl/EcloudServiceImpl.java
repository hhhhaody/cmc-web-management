package com.example.service.impl;

import com.example.pojo.Sts;
import com.example.service.EcloudService;
import com.example.utils.Ecloud;
import org.springframework.stereotype.Service;

@Service
public class EcloudServiceImpl implements EcloudService {

    @Override
    public Sts getSts() {
        return Ecloud.getAssumeRole();
    }
}
