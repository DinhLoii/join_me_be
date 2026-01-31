package com.join.me.modules.cloudinary.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.cloudinary.service.api.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryServiceImpl implements CloudinaryService {

    Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE = 20 * 1024 * 1024; // 20MB
    private static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"};
    private static final String[] ALLOWED_VIDEO_TYPES = {"video/mp4", "video/mpeg", "video/quicktime", "video/x-msvideo"};

    @Override
    public String uploadFile(MultipartFile file, String folder) {
        validateFile(file);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> params = (Map<String, Object>) ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "auto"
            );

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = (Map<String, Object>) cloudinary.uploader().upload(file.getBytes(), params);
            String publicUrl = (String) uploadResult.get("secure_url");

            if (publicUrl == null) {
                log.error("Cloudinary upload failed: no secure_url in response");
                throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
            }

            log.info("File uploaded successfully to Cloudinary: {}", publicUrl);
            return publicUrl;
        } catch (IOException e) {
            log.error("Error uploading file to Cloudinary", e);
            throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            log.error("File size exceeds limit: {} bytes", file.getSize());
            throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
        }

        boolean isAllowed = false;
        for (String allowedType : ALLOWED_IMAGE_TYPES) {
            if (contentType.equals(allowedType)) {
                isAllowed = true;
                break;
            }
        }
        if (!isAllowed) {
            for (String allowedType : ALLOWED_VIDEO_TYPES) {
                if (contentType.equals(allowedType)) {
                    isAllowed = true;
                    break;
                }
            }
        }

        if (!isAllowed) {
            log.error("File type not allowed: {}", contentType);
            throw new AppException(ErrorCode.SERVER_UNCATEGORIZED_EXCEPTION);
        }
    }
}
