package com.example.service;

import com.example.pojo.DefectiveOperation;
import com.example.pojo.Value;

import java.util.List;

public interface DefectiveOperationService {
    List<Value> searchField(String field);

    void insert(DefectiveOperation defectiveOperation);

    List<DefectiveOperation> getByBatch(String batch);
}
