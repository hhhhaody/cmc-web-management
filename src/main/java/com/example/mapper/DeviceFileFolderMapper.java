package com.example.mapper;

import com.example.pojo.DeviceFileFolder;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface DeviceFileFolderMapper {

    // 向tbl_folders表插入一个新的文件夹记录。
    @Insert("INSERT INTO tbl_folders (folder_name, created_at, updated_at) VALUES (#{folderName}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "folderId", keyColumn = "folder_id")
    void insert(DeviceFileFolder deviceFileFolder);

    // 更新tbl_folders表中的指定文件夹记录。
    @Update("UPDATE tbl_folders SET folder_name=#{folderName}, updated_at=#{updatedAt} WHERE folder_id=#{folderId}")
    void update(DeviceFileFolder deviceFileFolder);

    // 从tbl_folders表中删除指定的文件夹记录。
    @Delete("DELETE FROM tbl_folders WHERE folder_id=#{folderId}")
    void delete(Long folderId);

    // 根据关键字搜索tbl_folders表，并进行分页。
    @Select({
            "SELECT * FROM tbl_folders",
            "WHERE folder_name LIKE CONCAT('%', #{keyword}, '%')",
            "ORDER BY created_at DESC",
            "LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<DeviceFileFolder> search(String keyword, Integer offset, Integer pageSize);

    // 根据关键字计算tbl_folders表中匹配的记录数。
    @Select({
            "SELECT COUNT(*) FROM tbl_folders",
            "WHERE folder_name LIKE CONCAT('%', #{keyword}, '%')"
    })
    Long count(String keyword);


    /**
     * 查询tbl_folders表中某个字段的唯一值。
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from tbl_folders order by value")
    List<Value> searchField(@Param("field") String field);

    // 根据文件夹名称和字段名，查询tbl_folders表中某个字段的唯一值。
    @Select("select distinct ${field} as value from tbl_folders where folder_name like CONCAT('%',#{folder_name},'%') order by value")
    List<Value> searchByField(@Param("folderName") String folderName, @Param("field") String field);

}

