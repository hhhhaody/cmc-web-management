package com.example.utils;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 这是一个用于与海康威视开放平台进行通信的工具类，提供了获取摄像头预览URL和视频流URL的方法。
 */
public class HikOpenApi {

    // Artemis网关服务器的基础路径
    public final static String ARTEMIS = "/artemis";

    /**
     * 获取摄像头预览URL的方法
     *
     * @param host        Artemis网关服务器的主机地址
     * @param appKey      应用程序的AppKey
     * @param appSecret   应用程序的AppSecret
     * @param Url         接口的相对路径
     * @param jsonBodyMap 包含请求参数的Map
     * @return 返回从平台获取的结果
     */
    public static String GetCameraPreviewURL(String host, String appKey, String appSecret, String Url, Map<String, Object> jsonBodyMap) {

        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "218.18.137.246:444"; // artemis网关服务器ip端口
        ArtemisConfig.appKey = "27244685";  // 秘钥appkey
        ArtemisConfig.appSecret = "0jfaFeeKkreOEIFouSSE";// 秘钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = ARTEMIS;

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + Url;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 1000);
        jsonBody.putAll(jsonBodyMap);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);// post请求application/json类型参数
        return result;
    }

    /**
     * 获取视频流URL的方法
     *
     * @param indexCode 摄像头的索引代码
     * @return 返回从平台获取的结果
     */
    public static String GetVideoStreamURL(String indexCode) {
        // 设置API地址
        final String previewURLsApi = ARTEMIS + "/api/video/v2/cameras/previewURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);
            }
        };

        // 设置参数提交方式
        String contentType = "application/json";

        // 组装请求参数
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", indexCode);
        jsonBody.put("streamType", 0);
        jsonBody.put("protocol", "hls");
        jsonBody.put("transmode", 1);
        jsonBody.put("expand", "streamform=ps");
        jsonBody.put("streamform", "ps");
        String body = jsonBody.toJSONString();

        // 发送请求
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);
        return result;
    }

//    public static void main(String[] args) {
//        Map<String,Object> map = new HashMap<>();

//        String result = GetCameraPreviewURL("218.18.137.246:444", "27244685", "0jfaFeeKkreOEIFouSSE", "/api/nms/v1/online/camera/get", map);
//        System.out.println("result结果示例: " + result);
//        JSONObject jsonObject = JSON.parseObject(result);
//        JSONObject dataObject = jsonObject.getJSONObject("data");
//        JSONArray listArray = dataObject.getJSONArray("list");

        // 将JSONArray转换为List<Map<String, Object>>
//        List<Map> cameraList = listArray.toJavaList(Map.class);
//        System.out.println(cameraList);

//        for (Map<String, Object> camera : cameraList) {
//            String indexCode = (String) camera.get("indexCode");
//            String resultTest = GetVideoStreamURL(indexCode);
//            System.out.println("Video stream URL for indexCode " + indexCode + ": " + resultTest);
//        }
//    }
}
