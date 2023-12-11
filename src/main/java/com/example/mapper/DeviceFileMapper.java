package com.example.mapper;

import com.example.pojo.DeviceFile;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeviceFileMapper {

    // 向tbl_files表插入一个新的文件记录。
    @Insert("INSERT INTO tbl_files (folder_id, file_name, file_size, uploaded_at, updated_at) VALUES (#{folderId}, #{fileName}, #{fileSize}, #{uploadedAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId", keyColumn = "file_id")
    void insert(DeviceFile deviceFile);

    // 更新tbl_files表中的指定文件记录。
    @Update("UPDATE tbl_files SET file_name=#{fileName}, updated_at=#{updatedAt} WHERE file_id=#{fileId}")
    void update(DeviceFile deviceFile);

    // 从tbl_files表中删除指定的文件记录。
    @Delete("DELETE FROM tbl_files WHERE file_id=#{fileId}")
    void delete(Long fileId);

    // 根据关键字搜索tbl_files表，并进行分页。
    @Select({
            "SELECT * FROM tbl_files",
            "WHERE file_name LIKE CONCAT('%', #{keyword}, '%')",
            "ORDER BY updated_at DESC",
            "LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<DeviceFile> search(String keyword, Integer offset, Integer pageSize);

    // 根据关键字计算tbl_files表中匹配的记录数。
    @Select({
            "SELECT COUNT(*) FROM tbl_files",
            "WHERE file_name LIKE CONCAT('%', #{keyword}, '%')"
    })
    Long count(String keyword);

    // 检查tbl_files表中是否存在指定的文件名。
    @Select("SELECT COUNT(*) FROM tbl_files WHERE file_name = #{fileName}")
    int checkFileNameExists(String fileName);

    // 根据文件夹ID查询tbl_files表中的文件记录，并进行分页。
    @Select({
            "SELECT * FROM tbl_files",
            "WHERE folder_id = #{folderId}",
            "ORDER BY updated_at DESC",
            "LIMIT #{pageSize} OFFSET #{offset}"
    })
    List<DeviceFile> getFilesInFolder(Long folderId, Integer offset, Integer pageSize);

    // 根据文件夹ID计算tbl_files表中的文件记录数。
    @Select({
            "SELECT COUNT(*) FROM tbl_files",
            "WHERE folder_id = #{folderId}"
    })
    Long countFilesInFolder(Long folderId);


    /**
     * 查询tbl_files表中某个字段的唯一值。
     * @param field
     * @return
     */
    @Select("select distinct ${field} as value from tbl_files order by value")
    List<Value> searchFieldFile(@Param("field") String field);

    // 根据文件名和字段名，查询tbl_files表中某个字段的唯一值。
    @Select("select distinct ${field} as value from tbl_files where file_name like CONCAT('%',#{fileName},'%') order by value")
    List<Value> searchByFieldFile(@Param("fileName") String fileName, @Param("field") String field);

    // 根据ID查询文件详情
    @Select("SELECT * FROM tbl_files WHERE file_id = #{fileId}")
    DeviceFile findById(Long fileId);

}

