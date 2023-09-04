package com.peng.file.service;

import com.peng.file.config.MinioConfig;
import com.peng.file.config.MinioConfigProperties;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: pengshengfeng
 * @date: 2023/9/4 17:15
 * @description:
 */
@Slf4j
@EnableConfigurationProperties({MinioConfigProperties.class})
@Import(MinioConfig.class)
public class MinIOFileStorageService implements FileStorageService {
    private static final String SEPARATOR = "/";
    @Autowired
    private MinioConfigProperties minioConfigProperties;

    @Autowired
    private MinioClient minioClient;


    private String builderFilePath(String dirPath, String filePath) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(dirPath)) {
            sb.append(dirPath).append(SEPARATOR);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        sb.append(dateStr).append(SEPARATOR).append(filePath);
        return sb.toString();
    }


    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        String dir = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(dir)
                    .contentType("image/jpg")
                    .bucket(minioConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder sb = new StringBuilder(minioConfigProperties.getReadPath());
            sb.append(SEPARATOR).append(minioConfigProperties.getBucket()).append(SEPARATOR).append(dir);
            return sb.toString();
        } catch (Exception e) {
            log.error("Minio put file error:", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        String dir = builderFilePath(prefix, filename);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(dir)
                    .contentType("text/html")
                    .bucket(minioConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);
            StringBuilder sb = new StringBuilder(minioConfigProperties.getReadPath());
            sb.append(SEPARATOR).append(minioConfigProperties.getBucket()).append(SEPARATOR).append(dir);
            return sb.toString();
        } catch (Exception e) {
            log.error("Minio put file error:", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    @Override
    public void delete(String pathUrl) {
        String replace = pathUrl.replace(minioConfigProperties.getBucket() + "/", "");
        int index = replace.indexOf("/");
        String bucket = replace.substring(0, index);
        String filePath = replace.substring(index + 1);
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder().bucket(bucket).object(filePath).build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("Minio remote file error. pathUrl:{}", pathUrl);
            e.printStackTrace();
        }
    }

    @Override
    public byte[] downloadFile(String pathUrl) {
        String replace = pathUrl.replace(minioConfigProperties.getBucket() + "/", "");
        int index = replace.indexOf("/");
        String bucket = replace.substring(0, index);
        String filePath = replace.substring(index + 1);
        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(filePath).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}", pathUrl);
            e.printStackTrace();
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes = new byte[100];
        int rc = 0;
        while (true) {
            try {
                if (!((rc = inputStream.read(bytes, 0, 100)) > 0)) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            os.write(bytes, 0, rc);
        }

        return os.toByteArray();
    }
}
