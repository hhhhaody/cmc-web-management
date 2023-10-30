package com.example.mapper;

import com.example.pojo.DeviceFileOperation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceFileOperationMapper {

    // 向tbl_operations表插入一个新的操作记录。
    @Insert("INSERT INTO tbl_operations (operation_type, item_type, item_id, performed_at) VALUES (#{operationType}, #{itemType}, #{itemId}, #{performedAt})")
    void insert(DeviceFileOperation deviceFileOperation);
}

