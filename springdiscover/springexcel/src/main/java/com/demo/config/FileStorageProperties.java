package com.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jam
 * @description
 * @create 2018-05-15
 **/
@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
