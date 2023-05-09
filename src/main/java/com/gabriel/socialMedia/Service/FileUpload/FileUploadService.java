package com.gabriel.socialMedia.Service.FileUpload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String upload(MultipartFile file, String fileName);
}
