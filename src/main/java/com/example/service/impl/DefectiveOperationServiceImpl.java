package com.example.service.impl;

import com.example.mapper.DefectiveMapper;
import com.example.mapper.DefectiveOperationMapper;
import com.example.mapper.MaterialMapper;
import com.example.mapper.MaterialOperationMapper;
import com.example.pojo.DefectiveOperation;
import com.example.pojo.MaterialOperation;
import com.example.pojo.Value;
import com.example.service.DefectiveOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class DefectiveOperationServiceImpl implements DefectiveOperationService {
    @Autowired
    DefectiveOperationMapper defectiveOperationMapper;

    @Autowired
    DefectiveMapper defectiveMapper;

    @Autowired
    MaterialMapper materialMapper;

    @Autowired
    MaterialOperationMapper materialOperationMapper;

    @Override
    public List<Value> searchField(String field) {
        return defectiveOperationMapper.searchField(field);
    }

    @Transactional
    @Override
    public void insert(DefectiveOperation defectiveOperation) {
        if(defectiveOperation.getOperation().equals("返用")){
            MaterialOperation materialOperation= materialOperationMapper.getByBatchSimple(defectiveOperation.getBatch());
            materialOperation.setAmount(defectiveOperation.getAmount());
            materialOperation.setBatch(defectiveOperation.getBatch()+"F");
            materialOperation.setOperateTime(defectiveOperation.getOperateTime());
            materialOperation.setReceipt(defectiveOperation.getReceipt());
            materialOperation.setOperation("入库");
            materialOperation.setOperator(defectiveOperation.getOperator());
            materialOperationMapper.insert(materialOperation);
        }
//        String batch = defectiveOperation.getBatch();
//        if(defectiveOperation.getOperation().equals("报废")){
//            if(defectiveMapper.getByBatch(batch).getScrappedAmount() != null){
//                Integer scrappedAmount = defectiveMapper.getByBatch(batch).getScrappedAmount();
//                defectiveMapper.updateByBatch(batch,"scrappedAmount",defectiveOperation.getAmount()+scrappedAmount);
//            }
//            else{
//                defectiveMapper.updateByBatch(batch,"scrappedAmount",defectiveOperation.getAmount());
//            }
//        }
//        else{
//            if(defectiveMapper.getByBatch(batch).getReturnedAmount() != null){
//                Integer scrappedAmount = defectiveMapper.getByBatch(batch).getScrappedAmount();
//                defectiveMapper.updateByBatch(batch,"returnedAmount",defectiveOperation.getAmount()+scrappedAmount);
//            }
//            else{
//                defectiveMapper.updateByBatch(batch,"returnedAmount",defectiveOperation.getAmount());
//            }
//            MaterialOperation materialOperation= materialOperationMapper.getByBatchSimple(batch);
//            materialOperation.setAmount(defectiveOperation.getAmount());
//            materialOperation.setBatch(batch+"F");
//            materialOperation.setOperateTime(defectiveOperation.getOperateTime());
//            materialOperation.setReceipt(defectiveOperation.getReceipt());
//            materialOperation.setOperation("入库");
//            materialOperation.setOperator(defectiveOperation.getOperator());
//            materialOperationMapper.insert(materialOperation);
//        }
//        Integer defectiveAmount = defectiveMapper.getByBatch(batch).getDefectiveAmount();
//        defectiveMapper.updateByBatch(batch,"defectiveAmount",defectiveAmount-defectiveOperation.getAmount());
        defectiveOperationMapper.insert(defectiveOperation);
        defectiveMapper.calAmount();
    }

    @Override
    public List<DefectiveOperation> getByBatch(String batch) {
        String decode = null;
        try {
            decode = java.net.URLDecoder.decode(batch, "UTF-8");
            return defectiveOperationMapper.getByBatch(decode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
