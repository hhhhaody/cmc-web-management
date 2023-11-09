package com.example.service;

import com.example.pojo.*;

import java.util.List;

public interface DeviceFileService {

    /**
     * 创建一个新的文件夹。
     * @param folderName 要创建的文件夹的名称。
     */
    void createFolder(String folderName);

    /**
     * 上传一个文件。
     * @param folderId 上传文件的目标文件夹的ID。
     * @param fileName 上传文件的名称。
     * @param fileSize 上传文件的大小。
     * @return 最终保存的文件名。如果文件名已存在，它可能会被修改以避免冲突。
     */
    String uploadFile(Long folderId, String fileName, Long fileSize);

    /**
     * 重命名一个项（文件或文件夹）。
     * @param itemType 要重命名的项的类型（"FOLDER" 或 "FILE"）。
     * @param itemId 要重命名的项的ID。
     * @param newName 新的名称。
     * @return 最终保存的名称。如果名称已存在，它可能会被修改以避免冲突。
     */
    String renameItem(String itemType, Long itemId, String newName);

    /**
     * 删除一个项（文件或文件夹）。
     * @param itemType 要删除的项的类型（"FOLDER" 或 "FILE"）。
     * @param itemId 要删除的项的ID。
     * @return 删除操作的结果。
     */
    Result deleteItem(String itemType, Long itemId);

    /**
     * 获取所有的文件夹并进行分页处理。
     * @param keyword 用于搜索文件夹的关键字。
     * @param page 当前页码。
     * @param pageSize 每页的记录数量。
     * @return 包含文件夹列表和分页信息的PageBean对象。
     */
    PageBean getAllFolder(String keyword, Integer page, Integer pageSize);

    /**
     * 获取指定文件夹中的所有文件并进行分页处理。
     * @param folderId 要查询的文件夹的ID。
     * @param page 当前页码。
     * @param pageSize 每页的记录数量。
     * @param keyword 用于搜索文件的关键字。
     * @return 包含文件列表和分页信息的PageBean对象。
     */
    PageBean getAllFilesInFolder(Long folderId, Integer page, Integer pageSize, String keyword);

    /**
     * 根据给定的实体和字段名查询唯一值。
     * @param deviceFile 要查询的实体（可以是DeviceFileFolder或DeviceFile）。
     * @param field 要查询的字段名。
     * @return 唯一值的列表。
     */
    List<Value> searchByField(DeviceFile deviceFile, String field);

    /**
     * 根据字段名查询文件夹中的唯一值。
     * @param field 要查询的字段名。
     * @return 唯一值的列表。
     */
    List<Value> searchField(String field);

    /**
     * 根据字段名查询文件中的唯一值。
     * @param field 要查询的字段名。
     * @return 唯一值的列表。
     */
    List<Value> searchFieldFile(String field);
}
