package com.example.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private String endpoint = "https://eos-chengdu-1.cmecloud.cn";
    private String region = "chengdu1";
    private String accessKey = "0YVPMIATXNJTIHHR4W15";
    private String secretKey = "rlltM1E4rG9WuGwJfecyI3yRmFeBpNq0wbOTJl18";
    private String bucketName = "cmc/receipt";

    @Override
    public String storeFile(MultipartFile file, String fileName) throws IOException {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, region))
                .enablePathStyleAccess()
                .build();

        File fileObj = convertMultiPartFileToFile(file, fileName);
        try {
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, fileName, fileObj)
                    .withCannedAcl(CannedAccessControlList.PublicRead); // Set the file to be publicly readable
            s3client.putObject(putRequest);
            return s3client.getUrl(bucketName, fileName).toString(); // Returns the URL of the uploaded file
        } finally {
            Files.deleteIfExists(fileObj.toPath()); // Ensure the temp file is deleted whether the upload succeeds or fails
        }
    }

    private File convertMultiPartFileToFile(MultipartFile file, String fileName) throws IOException {
        Path tempDir = Files.createTempDirectory("upload_");
        File convFile = tempDir.resolve(fileName).toFile();
        file.transferTo(convFile);
        return convFile;
    }
}
