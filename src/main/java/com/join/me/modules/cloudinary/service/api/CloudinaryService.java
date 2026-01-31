package com.join.me.modules.cloudinary.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    String uploadFile(MultipartFile file, String folder);
}
