package com.example.controller;

import com.example.pojo.*;
import com.example.service.DeviceFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("/deviceFiles")
public class DeviceFileController {

    @Autowired
    private DeviceFileService deviceFileService;

    // 获取所有文件夹，支持关键字搜索和分页
    @GetMapping("/getAllFolder")
    public Result getAllFolder(@RequestParam(required = false) String keyword,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(deviceFileService.getAllFolder(keyword, page, pageSize));
    }

    // 获取指定文件夹内的所有文件，支持关键字搜索和分页
    @GetMapping("/getAllFilesInFolder")
    public Result getAllFilesInFolder(@RequestParam(required = true) Long folderId,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(required = false) String keyword) {
        return Result.success(deviceFileService.getAllFilesInFolder(folderId, page, pageSize, keyword));
    }

    // 创建一个新的文件夹
    @PostMapping("/createFolder")
    public Result createFolder(@RequestBody Map<String, String> body) {
        String folderName = body.get("name");
        log.info("创建文件夹：{}", folderName);
        deviceFileService.createFolder(folderName);
        return Result.success("创建成功");
    }

    // 上传文件信息
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam("fileName") String fileName,
                             @RequestParam("fileSize") Long fileSize,
                             @RequestParam("folderId") Long folderId) {
        log.info("接收文件信息：{}，文件大小：{}，文件夹ID：{}", fileName, fileSize, folderId);

        // 检查文件大小
        if (fileSize > 10485760) { // 10MB = 10 * 1024 * 1024 bytes
            return Result.error("文件大小超过限制（10MB）");
        }

        // 检查文件类型
        String[] allowedTypes = {".docx", ".pdf", ".txt"};
        boolean isAllowedType = false;

        for (String type : allowedTypes) {
            if (fileName.toLowerCase().endsWith(type)) {
                isAllowedType = true;
                break;
            }
        }

        if (!isAllowedType) {
            return Result.error("不支持的文件类型");
        }

        // 将文件信息保存到数据库
        String newFileName = deviceFileService.uploadFile(folderId, fileName, fileSize);

        return Result.success(newFileName);
    }

    // 重命名项（可以是文件或文件夹）
    @PutMapping("/renameItem/{itemId}")
    public Result renameItem(@PathVariable Long itemId, @RequestBody Map<String, String> body) {
        String newName = body.get("newName");
        String itemType = body.get("itemType");
        log.info("Received itemType from request body: {}", itemType);
        log.info("重命名 {} ID：{} 为：{}", itemType, itemId, newName);
        String newFileName = deviceFileService.renameItem(itemType, itemId, newName);
        return Result.success(newFileName);
    }

    // 删除项（可以是文件或文件夹）
    @DeleteMapping("/deleteItem/{itemId}")
    public Result deleteItem(@PathVariable Long itemId, @RequestParam String itemType) {
        log.info("删除 {} ID：{}", itemType, itemId);
        return deviceFileService.deleteItem(itemType, itemId); // 直接返回Service层的Result
    }

    // 根据字段名搜索数据（可以是文件名或文件夹名）
    @GetMapping("/search/{field}")
    public Result searchField(@PathVariable String field) {
        log.info("根据field查询已有数据：{}", field);

        // 根据字段名判断是查询文件还是文件夹
        if ("folderName".equalsIgnoreCase(field)) {
            List<Value> res = deviceFileService.searchField(field);
            return Result.success(res);
        } else if ("fileName".equalsIgnoreCase(field)) {
            List<Value> res = deviceFileService.searchFieldFile(field);
            return Result.success(res);
        } else {
            return Result.error("不支持的字段: " + field);
        }
    }

    // 根据用户输入的部分信息提供搜索建议
    // 搜索文件名
    @PostMapping("/search/{field}")
    public Result searchSuggestion(@RequestBody DeviceFile deviceFile, @PathVariable String field) {
        log.info("根据用户已输入信息查询已有数据：{},{}", deviceFile, field);
        List<Value> res = deviceFileService.searchByField(deviceFile, field);
        return Result.success(res);
    }

}
