package com.example.service.impl;

import com.example.mapper.ProductMapper;
import com.example.mapper.ProductOperationMapper;
import com.example.pojo.*;
import com.example.service.ProductOperationService;
import com.example.utils.Ecloud;
import com.example.utils.PinYinUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProductOperationServiceImpl implements ProductOperationService {

    @Autowired
    private ProductOperationMapper productOperationMapper;

    @Autowired
    private ProductMapper productMapper;

    @Transactional
    @Override
    public String insert(ProductOperation productOperation) {
        //计算product的数量
        productMapper.calAmount();

        //初次入库生成批次号
        if (productOperation.getOperation().equals("入库")){
            //拿到此规格物料的流水号用于生成批次号
            Integer batch = productMapper.getBatch(productOperation.getName(), productOperation.getSpec());
            String batchStr = PinYinUtil.getBatchString(productOperation.getName(), String.valueOf(batch), productOperation.getOperateTime());
            if(productOperation.getQuality().equals("返修件")) batchStr += "1";
            if(productOperation.getQuality().equals("废品")) batchStr += "2";
            productOperation.setBatch(batchStr);
        }

        productOperationMapper.insert(productOperation);
        return productOperation.getBatch();
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String operation, String name, String spec, LocalDateTime operateTimeStart, LocalDateTime operateTimeEnd, String quality, String operator) {
        PageHelper.startPage(page, pageSize);

        List<ProductOperation> productOperationList = productOperationMapper.list(operation, name, spec, operateTimeStart, operateTimeEnd, quality, operator);
        Page<ProductOperation> p = (Page<ProductOperation>) productOperationList;

        PageBean pageBean = new PageBean(p.getTotal(), p.getResult());

        return pageBean;
    }

    @Override
    public List<Value> searchField(String field) {
        return productOperationMapper.searchField(field);
    }

    @Override
    public List<Value> search(ProductOperation productOperation, String field) {
        String name = productOperation.getName();
        String spec = productOperation.getSpec();
        String batch = productOperation.getBatch();
        return productOperationMapper.search(name,spec,batch,field);
    }

    @Override
    public ProductOperation getByBatch(String batch) {
        String decode = null;
        try {
            decode = java.net.URLDecoder.decode(batch, "UTF-8");
            return productOperationMapper.getByBatch(decode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProductOperation getById(Integer id) {
        return productOperationMapper.getById(id);
    }
    @Transactional
    @Override
    public void deleteById(Integer id) {
        ProductOperation productOperation = productOperationMapper.getById(id);
        String receipt =  productOperation.getReceipt();
        productOperationMapper.deleteById(id);

        // 从EOS删除相关凭证
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
    public void update(ProductOperation productOperation) {
        productOperationMapper.update(productOperation);
    }
}
