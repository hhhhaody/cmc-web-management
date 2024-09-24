package com.example.utils;
import com.example.pojo.Sts;
import lombok.experimental.UtilityClass;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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

        // 初始化 STS client。
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AWSSecurityTokenService stsClient = AWSSecurityTokenServiceClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();

        // 获取 STS access key、secret access key、token，过期时间为 900 秒。
        GetSessionTokenRequest request = new GetSessionTokenRequest().withDurationSeconds(7200);
        GetSessionTokenResult result = stsClient.getSessionToken(request);
        System.out.println("sts ak: " + result.getCredentials().getAccessKeyId());
        System.out.println("sts sk: " + result.getCredentials().getSecretAccessKey());
        System.out.println("sts token: " + result.getCredentials().getSessionToken());

        // 关闭 stsClient。
        stsClient.shutdown();
        return new Sts(result.getCredentials().getAccessKeyId(),result.getCredentials().getSecretAccessKey(),result.getCredentials().getSessionToken());

    }


    public static void delete(String name){
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。

        // 以华东 - 苏州为例，endpoint 填写 https://eos-wuxi-1.cmecloud.cn，Region 填写 wuxi1。
        String endpoint = "https://eos-chengdu-1.cmecloud.cn";
        String region = "chengdu1";

// 填写 EOS 账号的认证信息，或者子账号的认证信息。
        String accessKey = "0YVPMIATXNJTIHHR4W15";
        String secretKey = "rlltM1E4rG9WuGwJfecyI3yRmFeBpNq0wbOTJl18";

// 桶名。
        String bucketName = "cmc";
// 填写文件名，例如'object.txt'。
        String objectName = name;

// 创建 AmazonS3 实例。
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        AmazonS3 client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider).build();
        // 填写存储桶（Bucket）所在地域对应的 endpoint 和 Region。


// 简单列举。
//        ObjectListing objects = client.listObjects(bucketName);
//        for (S3ObjectSummary s3ObjectSummary: objects.getObjectSummaries()) {
//            System.out.println(s3ObjectSummary.getKey());
//        }

// 删除单个文件（Object）。
        client.deleteObject(bucketName, objectName);

// 关闭 client。
        client.shutdown();

    }


    public static void main(String[] args){
        getAssumeRole();
//        getUrl();
//        delete("490697569156988928.jpg");
    }
}
