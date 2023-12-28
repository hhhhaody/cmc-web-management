package com.example.service.impl;

import com.example.mapper.DefectiveMapper;
import com.example.mapper.DefectiveOperationMapper;
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

    @Autowired
    private DefectiveMapper defectiveMapper;

    @Autowired
    private DefectiveOperationMapper defectiveOperationMapper;

    @Transactional
    @Override
    public String insert(MaterialOperation materialOperation) {
        materialMapper.calAmount();


        //初次入库生成批次号
        if (materialOperation.getOperation().equals("入库")){
            //拿到此规格物料的流水号用于生成批次号
            Integer batch = materialMapper.getBatch(materialOperation.getName(), materialOperation.getSpec());
            materialOperation.setBatch(PinYinUtil.getBatchString(materialOperation.getName(), String.valueOf(batch), materialOperation.getOperateTime()));
        }

        //转入不良物料库
        if (materialOperation.getOperation().equals("转入不良物料库")){
            String batch = materialOperation.getBatch();
            if(batch.endsWith("F")){
                batch=batch.replace("F","");
            }
            //假如不存在，生成新entry
            if(defectiveMapper.getByBatch(batch)==null){
                Defective defective = new Defective();
                defective.setBatch(materialOperation.getBatch());
                defective.setName(materialOperation.getName());
                defective.setSpec(materialOperation.getSpec());
                defective.setDefectiveAmount(materialOperation.getAmount());
                defective.setSupplier(materialOperation.getSupplier());
                defective.setCreateTime(materialOperation.getOperateTime());
                defectiveMapper.insert(defective);
            }

        }
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
            if(decode.endsWith("F")) return materialOperationMapper.getByBatchReturned(decode);
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
        MaterialOperation materialOperation = materialOperationMapper.getById(id);
        String receipt =  materialOperation.getReceipt();
        String batch =  materialOperation.getBatch();
        Integer amount = materialOperation.getAmount();
        LocalDateTime operateTime = materialOperation.getOperateTime();
        materialOperationMapper.deleteById(id);

        if(batch.endsWith("F")){
            defectiveOperationMapper.deleteByOperateTime(operateTime);
        }

        if(materialOperation.getOperation().equals("转入不良物料库")){
        if(defectiveMapper.getByBatch(batch.replace("F","")) != null){
            Integer defectiveAmount = defectiveMapper.getByBatch(batch.replace("F","")).getDefectiveAmount();
            Integer newAmount = defectiveAmount-amount;
            //如果不良数==0，直接从不良物料库删除此条目
            if(newAmount == 0){
                defectiveMapper.deleteByBatch(batch);
            } else {
                throw new RuntimeException("无法删除此条目，不良物料已返用或报废");
            }
        }}





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
    @Transactional
    public void update(MaterialOperation materialOperation) {

        LocalDateTime original = materialOperationMapper.getById(materialOperation.getId()).getSupplyTime();
        LocalDateTime supplyTime = materialOperation.getSupplyTime();

        //如果供料日期变化，更新此批次所有条目中的供料日期
        if(!original.isEqual(supplyTime)){
            String decode = null;
            String batch =  materialOperation.getBatch();
            try {
                decode = java.net.URLDecoder.decode(batch, "UTF-8");
                materialOperationMapper.updateSupplyTime(decode,supplyTime);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
//            materialOperationMapper.updateSupplyTime(batch,supplyTime);
        }
        materialOperationMapper.update(materialOperation);




        if(materialOperation.getBatch().endsWith("F")){
            System.out.println("更新不良物料库操作");
            System.out.println("-------------------------------------------");
            defectiveOperationMapper.updateByOperateTime(materialOperation);
        }
    }

    @Override
    public List<Value> searchAdvance(MaterialOperation materialOperation, String field) {
        String name = materialOperation.getName();
        String spec = materialOperation.getSpec();
        String operation = materialOperation.getOperation();
        String supplier = materialOperation.getSupplier();
        String operator = materialOperation.getOperator();

        return materialOperationMapper.searchAdvance(name,spec,operation,supplier,operator,field);
    }
}
