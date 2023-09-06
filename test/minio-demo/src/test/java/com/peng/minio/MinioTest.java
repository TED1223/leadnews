package com.peng.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 16:53
 * @description:
 */
public class MinioTest {


    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("c:\\index.html");

            MinioClient minioClient = MinioClient.builder()
                    .credentials("minio","minio")
                    .endpoint("http://127.0.0.1:9000")
                    .build();

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("index.html")
                    .contentType("text/html")
                    .stream(fileInputStream,fileInputStream.available(),-1)
                    .build();
            minioClient.putObject(putObjectArgs);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
