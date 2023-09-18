package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.service.CameraService;
import com.example.utils.HikOpenApi;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * 这是一个实现了CameraService接口的服务类，用于处理摄像头相关的业务逻辑。
 */
@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    private JdbcTemplate jdbcTemplate;  // 用于执行数据库操作的JdbcTemplate

    // 存储所有摄像头信息的列表，线程安全的
    private List<Map> allCameraInfo = Collections.synchronizedList(new ArrayList<>());

    /**
     * 获取摄像头列表的方法
     *
     * @return 包含过滤后摄像头信息的JSON字符串
     */
    @Override
    public String getCameraList() {
        Map<String, Object> map = new HashMap<>();
        // 调用海康威视开放平台API获取摄像头预览URL
        String cameraListJson = HikOpenApi.GetCameraPreviewURL("218.18.137.246:444", "27244685", "0jfaFeeKkreOEIFouSSE", "/api/nms/v1/online/camera/get", map);

        // 解析并存储所有的监控点位信息
        JSONObject jsonObject = JSON.parseObject(cameraListJson);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        allCameraInfo = list.toJavaList(Map.class);

        List<Map<String, Object>> filteredList = new ArrayList<>();

        // 复制原始摄像头信息到新列表
        List<Map> oldList = allCameraInfo; // 原来的 List<Map>
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Map oldMap : oldList) {
            Map<String, Object> newMap = new HashMap<>();
            for (Object key : oldMap.keySet()) {
                newMap.put((String) key, oldMap.get(key));
            }
            newList.add(newMap);
        }

        // 过滤摄像头信息
        for (Map<String, Object> cameraInfo : allCameraInfo) {
            Map<String, Object> filteredMap = new HashMap<>();
            filteredMap.put("regionName", cameraInfo.get("regionName"));
            filteredMap.put("indexCode", cameraInfo.get("indexCode"));
            filteredMap.put("online", cameraInfo.get("online"));
            filteredMap.put("cn", cameraInfo.get("cn"));

            filteredList.add(filteredMap);
        }

        // 将过滤后的信息保存到数据库
        saveFilteredCameraList(filteredList);

        // 返回过滤后的摄像头信息的JSON字符串
        return JSON.toJSONString(filteredList, true);
        // return allCameraInfo.toString();
    }

    /**
     * 获取摄像头的视频流URL的方法
     *
     * @param indexCode 摄像头的索引代码
     * @return 摄像头视频流URL或错误信息
     */
    @Override
    public String getVideoStream(String indexCode) {
        // 查询数据库以获取摄像头信息
        String querySql = "SELECT * FROM camera_info WHERE indexCode = ?";
        try {
            Map<String, Object> camera = jdbcTemplate.queryForMap(querySql, new Object[]{indexCode});

            if (camera != null && !camera.isEmpty()) {
                Integer online = (Integer) camera.get("online");
                if (online != null && online == 1) {
                    // 摄像头在线，调用海康威视开放平台API获取视频流URL
                    return HikOpenApi.GetVideoStreamURL(indexCode);
                } else {
                    return "摄像头离线";
                }
            }
        } catch (Exception e) {
            // 捕获异常，比如查询没有结果的情况
            // 这里可以打印日志或者做其他处理
            e.printStackTrace();
        }
        return "找不到摄像头";
    }

    /**
     * 保存过滤后的摄像头信息到数据库
     *
     * @param filteredList 过滤后的摄像头信息列表
     */
    public void saveFilteredCameraList(List<Map<String, Object>> filteredList) {
        String insertSql = "INSERT INTO camera_info (regionName, indexCode, online, cn) VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE camera_info SET regionName = ?, online = ?, cn = ? WHERE indexCode = ?";
        String querySql = "SELECT COUNT(*) FROM camera_info WHERE indexCode = ?";

        for (Map<String, Object> camera : filteredList) {
            Object[] values = new Object[]{
                    camera.get("regionName"),
                    camera.get("indexCode"),
                    camera.get("online"),
                    camera.get("cn")
            };

            String indexCode = camera.get("indexCode").toString();
            Integer count = jdbcTemplate.queryForObject(querySql, new Object[]{indexCode}, Integer.class);

            if (count != null && count == 0) {
                // 进行插入操作
                jdbcTemplate.update(insertSql, values);
            } else {
                // 进行更新操作
                Object[] updateValues = new Object[]{
                        camera.get("regionName"),
                        camera.get("online"),
                        camera.get("cn"),
                        camera.get("indexCode")
                };
                jdbcTemplate.update(updateSql, updateValues);
            }
        }

    }
}

