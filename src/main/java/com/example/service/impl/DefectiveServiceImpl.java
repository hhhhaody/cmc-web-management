package com.example.service.impl;

import com.example.mapper.DefectiveMapper;
import com.example.pojo.Defective;
import com.example.pojo.PageBean;
import com.example.pojo.Value;
import com.example.service.DefectiveService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class DefectiveServiceImpl implements DefectiveService {
    @Autowired
    DefectiveMapper defectiveMapper;

    @Override
    public PageBean page(Integer page, Integer pageSize, String name, String spec, String supplier) {
        defectiveMapper.calAmount();

        PageHelper.startPage(page,pageSize);

        List<Defective> defectiveList = defectiveMapper.list(name,spec,supplier);
        Page<Defective> p = (Page<Defective>) defectiveList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());
        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return defectiveMapper.searchField(field);
    }

    @Override
    public List<Value> search(Defective defective, String field) {
        String name = defective.getName();
        String spec = defective.getSpec();
        String batch = defective.getBatch();
        return defectiveMapper.search(name,spec,batch,field);
    }

    @Override
    public Defective getByBatch(String batch) {
        String decode = null;
        try {
            decode = java.net.URLDecoder.decode(batch, "UTF-8");
            return defectiveMapper.getByBatch(decode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Value> searchAdvance(Defective defective, String field) {
        String name = defective.getName();
        String spec = defective.getSpec();
        String supplier = defective.getSupplier();
        return defectiveMapper.searchAdvance(name,spec,supplier,field);
    }

}
