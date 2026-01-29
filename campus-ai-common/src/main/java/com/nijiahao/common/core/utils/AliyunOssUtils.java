package com.nijiahao.common.core.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AliyunOssUtils {

    private static final String ENDPOINT = "https://oss-cn-shenzhen.aliyuncs.com";
    private static final String BUCKET_NAME = "campusai-nijiahao-nijiahao";
    private static final String REGION = "cn-shenzhen";

    private static OSS getOssClient() throws Exception {
        // 使用环境变量凭证
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        conf.setSignatureVersion(SignVersion.V4);

        return OSSClientBuilder.create()
                .endpoint(ENDPOINT)
                .credentialsProvider(credentialsProvider)
                .region(REGION)
                .build();
    }



    /**
     * 上传字符串内容
     */
    public static String uploadString(String objectName, String content) {
        return uploadStream(objectName, new ByteArrayInputStream(content.getBytes()));
    }

    /**
     * 上传文件流
     */
    public static String uploadStream(String objectName, InputStream inputStream) {
        OSS ossClient = null;
        try {
            ossClient = getOssClient();
            ossClient.putObject(BUCKET_NAME, objectName, inputStream);
            return "https://" + BUCKET_NAME + "." + ENDPOINT.replace("https://", "") + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) ossClient.shutdown();
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String objectName) {
        OSS ossClient = null;
        try {
            ossClient = getOssClient();
            ossClient.deleteObject(BUCKET_NAME, objectName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ossClient != null) ossClient.shutdown();
        }
    }

    /**
     * 列出所有文件名称
     */
    public static List<String> listFileNames() {
        OSS ossClient = null;
        List<String> fileList = new ArrayList<>();
        try {
            ossClient = getOssClient();
            ObjectListing objectListing = ossClient.listObjects(BUCKET_NAME);
            for (OSSObjectSummary summary : objectListing.getObjectSummaries()) {
                fileList.add(summary.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) ossClient.shutdown();
        }
        return fileList;
    }

    /**
     * 获取文本文件的内容
     * @param objectName OSS中的文件名 (如 "config/data.json")
     * @return 文件内容的字符串
     */
    public static String getFileContentAsString(String objectName) {
        OSS ossClient = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            ossClient = getOssClient();
            // 获取 OSS 对象
            OSSObject ossObject = ossClient.getObject(BUCKET_NAME, objectName);

            // 读取流
            reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                content.append(line).append("\n"); // 这里可以根据需求决定是否加换行符
            }
            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关流、关客户端
            try {
                if (reader != null) reader.close();
            } catch (Exception e) { e.printStackTrace(); }
            if (ossClient != null) ossClient.shutdown();
        }
    }

}
