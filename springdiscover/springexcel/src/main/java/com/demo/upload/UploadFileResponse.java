package com.demo.upload;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jam
 * @description
 * @create 2018-05-15
 **/
@Data
@NoArgsConstructor
public class UploadFileResponse {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
