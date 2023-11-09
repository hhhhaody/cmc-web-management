package com.example.service.impl;

import com.example.mapper.DeviceFileFolderMapper;
import com.example.mapper.DeviceFileMapper;
import com.example.mapper.DeviceFileOperationMapper;
import com.example.pojo.*;
import com.example.service.DeviceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeviceFileServiceImpl implements DeviceFileService {
    @Autowired
    private DeviceFileFolderMapper deviceFileFolderMapper;

    @Autowired
    private DeviceFileMapper deviceFileMapper;

    @Autowired
    private DeviceFileOperationMapper deviceFileOperationMapper;

    /**
     * 创建一个新的文件夹。
     * @param folderName 文件夹名称
     */
    @Override
    public void createFolder(String folderName) {
        DeviceFileFolder folder = new DeviceFileFolder();
        folder.setFolderName(folderName);
        folder.setCreatedAt(new Date());
        folder.setUpdatedAt(new Date());
        deviceFileFolderMapper.insert(folder);

        logOperation("CREATE", "FOLDER", folder.getFolderId());
    }

    /**
     * 上传文件。如果文件名已存在，将自动重命名文件。
     * @param folderId 文件夹ID
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @return 新的文件名
     */
    @Override
    public String uploadFile(Long folderId, String fileName, Long fileSize) {
//        String originalFilename = fileName;
        int count = deviceFileMapper.checkFileNameExists(fileName);
        int suffix = 1;
        String baseName = removeFileExtension(fileName); // 移除文件扩展名
        String fileExtension = getFileExtension(fileName); // 获取文件扩展名

        // 如果文件名已存在，为其添加后缀
        while (count > 0) {
            fileName = baseName + "(" + suffix + ")" + fileExtension; // 插入递增数字
            count = deviceFileMapper.checkFileNameExists(fileName);
            suffix++;
        }
        DeviceFile file = new DeviceFile();
        file.setFolderId(folderId);
        file.setFileName(fileName);
        file.setFileSize(fileSize);
        file.setUploadedAt(new Date());
        file.setUpdatedAt(new Date());
        deviceFileMapper.insert(file);

        logOperation("UPLOAD", "FILE", file.getFileId());
        return fileName;  // 返回新的文件名
    }

    // 移除文件扩展名的辅助函数
    private String removeFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    // 获取文件扩展名的辅助函数
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }

    /**
     * 重命名文件或文件夹。如果是文件并且文件名已存在，则自动重命名文件。
     * @param itemType 文件或文件夹
     * @param itemId 文件或文件夹ID
     * @param newName 新名称
     * @return 新的名称
     */
    @Override
    public String renameItem(String itemType, Long itemId, String newName) {
        if ("FOLDER".equals(itemType)) {
            DeviceFileFolder folder = new DeviceFileFolder();
            folder.setFolderId(itemId);
            folder.setFolderName(newName);
            folder.setUpdatedAt(new Date());
            deviceFileFolderMapper.update(folder);
        } else {
//            String originalFilename = newName;
            int count = deviceFileMapper.checkFileNameExists(newName);
            int suffix = 1;
            String baseName = removeFileExtension(newName);
            String fileExtension = getFileExtension(newName);

            while (count > 0) {
                newName = baseName + "(" + suffix + ")" + fileExtension;
                count = deviceFileMapper.checkFileNameExists(newName);
                suffix++;
            }

            DeviceFile file = new DeviceFile();
            file.setFileId(itemId);
            file.setFileName(newName);
            file.setUpdatedAt(new Date());
            deviceFileMapper.update(file);
        }

        logOperation("RENAME", itemType, itemId);
        return newName;  // 返回新的文件名
    }

    /**
     * 删除文件或文件夹。
     * @param itemType 文件或文件夹
     * @param itemId 文件或文件夹ID
     * @return 操作结果
     */
    @Override
    public Result deleteItem(String itemType, Long itemId) {
        if ("FOLDER".equals(itemType)) {
            Long fileCount = deviceFileMapper.countFilesInFolder(itemId);
            if (fileCount > 0) {
                return Result.error("该文件夹内有文件，无法删除");
            }
            deviceFileFolderMapper.delete(itemId);
        } else {
            deviceFileMapper.delete(itemId);
        }

        logOperation("DELETE", itemType, itemId);
        return Result.success("删除成功");
    }

    /**
     * 获取文件夹的分页列表。
     * @param keyword 搜索关键字
     * @param page 页码
     * @param pageSize 每页数量
     * @return 分页数据
     */
    @Override
    public PageBean getAllFolder(String keyword, Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;

        List<DeviceFileFolder> folders = deviceFileFolderMapper.search(keyword, offset, pageSize);

        Long totalFolders = deviceFileFolderMapper.count(keyword);

        return new PageBean(totalFolders, folders);
    }

    /**
     * 根据文件夹ID和关键字，获取文件的分页列表。
     * @param folderId 文件夹ID
     * @param page 页码
     * @param pageSize 每页数量
     * @param keyword 搜索关键字
     * @return 分页数据
     */
    @Override
    public PageBean getAllFilesInFolder(Long folderId, Integer page, Integer pageSize, String keyword) {
        int offset = (page - 1) * pageSize;
        List<DeviceFile> files;
        Long totalFiles;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 当提供了关键字时，使用search方法
            files = deviceFileMapper.search(keyword, offset, pageSize);
            totalFiles = deviceFileMapper.count(keyword);
        } else {
            // 否则使用getFilesInFolder方法
            files = deviceFileMapper.getFilesInFolder(folderId, offset, pageSize);
            totalFiles = deviceFileMapper.countFilesInFolder(folderId);
        }

        return new PageBean(totalFiles, files);
    }

    // 记录操作日志的辅助方法
    private void logOperation(String operationType, String itemType, Long itemId) {
        DeviceFileOperation operation = new DeviceFileOperation();
        operation.setOperationType(operationType);
        operation.setItemType(itemType);
        operation.setItemId(itemId);
        operation.setPerformedAt(new Date());
        deviceFileOperationMapper.insert(operation);
    }

    /**
     * 根据实体类型（文件夹或文件）和指定字段查询已有数据。
     * @param DeviceFile 实体对象（可以是文件夹或文件）
     * @param field 要查询的字段名称
     * @return 字段的唯一值列表
     */
    @Override
    public List<Value> searchByField(DeviceFile deviceFile, String field) {
//        if (entity instanceof DeviceFileFolder) {
//            DeviceFileFolder deviceFileFolder = (DeviceFileFolder) entity;
//            return deviceFileFolderMapper.searchByField(deviceFileFolder.getFolderName(), camelToSnake(field));
//        } else if (entity instanceof DeviceFile) {
//            DeviceFile deviceFile = (DeviceFile) entity;
//            return deviceFileMapper.searchByFieldFile(deviceFile.getFileName(), camelToSnake(field));
//        } else {
//            throw new IllegalArgumentException("Invalid entity type: " + entity.getClass().getSimpleName());
//        }
        String fileName = deviceFile.getFileName();
        return deviceFileMapper.searchByFieldFile(fileName,field);
    }

    /**
     * 查询文件夹表中指定字段的已有数据。
     * @param field 要查询的字段名称
     * @return 字段的唯一值列表
     */
    @Override
    public List<Value> searchField(String field) {
        return deviceFileFolderMapper.searchField(camelToSnake(field));
    }

    /**
     * 查询文件表中指定字段的已有数据。
     * @param field 要查询的字段名称
     * @return 字段的唯一值列表
     */
    @Override
    public List<Value> searchFieldFile(String field) {
        return deviceFileMapper.searchFieldFile(camelToSnake(field));
    }

    /**
     * 将驼峰命名的字符串转换为下划线命名。
     * 例如：camelToSnake("helloWorld") 返回 "hello_world"
     * @param str 要转换的驼峰命名的字符串
     * @return 转换后的下划线命名的字符串
     */
    private String camelToSnake(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
