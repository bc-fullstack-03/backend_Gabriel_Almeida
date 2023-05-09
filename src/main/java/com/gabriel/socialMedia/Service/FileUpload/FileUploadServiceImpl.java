package com.gabriel.socialMedia.Service.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadServiceImpl implements FileUploadService{

    @Autowired
    private AwsService awsService;

    public String upload(MultipartFile file, String fileName) {
        var fileUri = "";

        try {
            fileUri = awsService.upload(file, fileName);
        } catch (Exception e) {

        }

        return fileUri;
    }
}
