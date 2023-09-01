package com.example.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.example.pojo.Sts;
import lombok.experimental.UtilityClass;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@UtilityClass

public class Ecloud {
    public static Sts getAssumeRole(){
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。
// 以华东 - 苏州为例，endpoint 填写 https://eos-wuxi-1.cmecloud.cn，Region 填写 wuxi1。
        String endpoint = "https://eos-chengdu-1.cmecloud.cn";
        String region = "chengdu1";

// 填写 EOS 账号的认证信息，或者子账号的认证信息。
        String accessKey = "0YVPMIATXNJTIHHR4W15";
        String secretKey = "rlltM1E4rG9WuGwJfecyI3yRmFeBpNq0wbOTJl18";

// 桶名。
        String bucketName = "cmc";
// 文件名。
        String objectName = "default.jpg";

// 初始化 STS client
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();

// 获取临时认证信息
// policy 可以用来设置临时认证信息的权限、可访问资源等信息
        String policy = "{" +
                "\"Version\":\"2012-10-17\"," +
                "\"Statement\":[" +
                "{" +
                "\"Action\":[" +
                "\"s3:GetObject\"" +
                "]," +
                "\"Resource\":[" +
                "\"arn:aws:s3:::" + bucketName + "/" + objectName +
                "\"" + "]," +
                "\"Effect\":\"Allow\"," +
                "\"Sid\":\"VisualEditor0\"" +
                "}" +
                "]" +
                "}";
//        AssumeRoleRequest request = new AssumeRoleRequest().withDurationSeconds(7200).withPolicy(policy);
        AssumeRoleRequest request = new AssumeRoleRequest().withDurationSeconds(7200);
        AssumeRoleResult result = stsClient.assumeRole(request);
        System.out.println("sts ak: " + result.getCredentials().getAccessKeyId());
        System.out.println("sts sk: " + result.getCredentials().getSecretAccessKey());
        System.out.println("sts token: " + result.getCredentials().getSessionToken());



// 关闭 stsClient。
        stsClient.shutdown();

// 依据返回的临时认证信息，初始化 S3 client。
//        BasicSessionCredentials stsCredentials = new BasicSessionCredentials(
//                result.getCredentials().getAccessKeyId(),
//                result.getCredentials().getSecretAccessKey(),
//                result.getCredentials().getSessionToken());
//        AWSCredentialsProvider stsCredentialsProvider = new AWSStaticCredentialsProvider(stsCredentials);
//        AmazonS3 client = AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(endpointConfiguration)
//                .withCredentials(stsCredentialsProvider).build();

//// 生成共享外链。
//        GeneratePresignedUrlRequest request1 =
//                new GeneratePresignedUrlRequest(bucketName, objectName);
//// 设置过期时间，当到达该时间点时，URL 就会过期，其他人不再能访问该文件（Object）。
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date expiration = null;
//        try {
//            expiration = simpleDateFormat.parse("2023/8/28 23:59:59");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

// 设置 1 小时后过期。
// Date expiration = new java.util.Date();
// long expTimeMillis = Instant.now().toEpochMilli();
// expTimeMillis += 1000 * 60 * 60;
// expiration.setTime(expTimeMillis);

// 设置外链的访问方法为 PUT，默认为 GET。
// request.setMethod(HttpMethod.PUT);

//        request1.setExpiration(expiration);
//        URL url = client.generatePresignedUrl(request1);
//        System.out.println(url);


// 关闭 client。
//        client.shutdown();



//// 使用临时认证信息，执行操作。
//        S3Object s3Object = client.getObject(bucketName, objectName);
//        System.out.println(s3Object.getObjectContent());
//
//// 关闭 client。
//        client.shutdown();
        return new Sts(result.getCredentials().getAccessKeyId(),result.getCredentials().getSecretAccessKey(),result.getCredentials().getSessionToken());

    }

    public static void getUrl(){
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。
// 以华东 - 苏州为例，endpoint 填写 https://eos-wuxi-1.cmecloud.cn，Region 填写 wuxi1。
        String endpoint = "https://eos-chengdu-1.cmecloud.cn";
        String region = "chengdu1";

// 填写 EOS 账号的认证信息，或者子账号的认证信息。
        String accessKey = "0YVPMIATXNJTIHHR4W15";
        String secretKey = "rlltM1E4rG9WuGwJfecyI3yRmFeBpNq0wbOTJl18";

// 桶名。
        String bucketName = "cmc";
// 文件名。
        String objectName = "default.jpg";

// 创建 AmazonS3 实例。
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("S3SignerType");
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withClientConfiguration(clientConfiguration)
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();

// 生成可预览的外链。
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, objectName);

// 设置过期时间，当到达该时间点时，URL 就会过期，其他人不再能访问该文件（Object）。
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date expiration = null;
        try {
            expiration = simpleDateFormat.parse("2023/12/31 23:59:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        request.setExpiration(expiration);

// 设置返回头
// 设置为 "inline" 时在浏览器中展示，设置为 "attachment" 时以文件形式下载。
// 此外设置为 "attachment;filename=\"filename.jpg\"" ，还可以让下载的文件名字重命名为 "filename.jpg"。
        ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides();
        headerOverrides.setContentDisposition("inline");
        request.setResponseHeaders(headerOverrides);

        URL url = client.generatePresignedUrl(request);
        System.out.println(url);

// 关闭 client
        client.shutdown();

    }



    public static void main(String[] args){
        getAssumeRole();
//        getUrl();
    }
}
