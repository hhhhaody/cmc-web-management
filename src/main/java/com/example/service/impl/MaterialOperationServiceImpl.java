package com.example.service.impl;

import com.example.mapper.MaterialMapper;
import com.example.mapper.MaterialOperationMapper;
import com.example.pojo.*;
import com.example.service.MaterialOperationService;
import com.example.utils.Ecloud;
import com.example.utils.PinYinUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;

@Service
public class MaterialOperationServiceImpl implements MaterialOperationService {

    @Autowired
    private MaterialOperationMapper materialOperationMapper;
    @Autowired
    private MaterialMapper materialMapper;

    @Transactional
    @Override
    public String insert(MaterialOperation materialOperation) {
        materialMapper.calAmount();

        Integer batch = materialMapper.getBatch(materialOperation.getName(), materialOperation.getSpec());

        if (materialOperation.getOperation().equals("入库")){
            materialOperation.setBatch(PinYinUtil.getBatchString(materialOperation.getName(), String.valueOf(batch), materialOperation.getOperateTime()));
        }

        //TODO: 转入不良物料库
        materialOperationMapper.insert(materialOperation);
        return materialOperation.getBatch();
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String supplier, String operator) {
        PageHelper.startPage(page, pageSize);

        List<MaterialOperation> materialOperationList = materialOperationMapper.list(operation, name, spec, operateTimeStart, operateTimeEnd, supplier, operator);
        Page<MaterialOperation> p = (Page<MaterialOperation>) materialOperationList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return materialOperationMapper.searchField(field);
    }

    @Override
    public List<Value> search(MaterialOperation materialOperation, String field) {
        String name = materialOperation.getName();
        String spec = materialOperation.getSpec();
        String batch = materialOperation.getBatch();
        return materialOperationMapper.search(name,spec,batch,field);
    }

    @Override
    public MaterialOperation getByBatch(String batch) {
        String decode = null;
        try {
            decode = java.net.URLDecoder.decode(batch, "UTF-8");
            return materialOperationMapper.getByBatch(decode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MaterialOperation getById(Integer id) {
        return materialOperationMapper.getById(id);
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        String receipt =  materialOperationMapper.findById(id);
        materialOperationMapper.deleteById(id);
        // 创建 Gson 实例
        Gson gson = new Gson();

        // 使用 Gson 解析 JSON 字符串为 Map
        Map<String, String> dictionary = gson.fromJson(receipt, Map.class);

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            String name = entry.getValue();
            System.out.println(name);
            System.out.println("-------------------------------------------");
            Ecloud.delete("receipt/"+name);
        }
    }

    @Override
    @Transactional
    public void update(MaterialOperation materialOperation) {
        String batch = materialOperation.getBatch();
        String supplier = materialOperation.getSupplier();
        LocalDateTime supplyTime =  materialOperation.getSupplyTime();
        materialOperationMapper.update(materialOperation);
        materialOperationMapper.updateByBatch(batch,supplier,supplyTime);
    }
}
