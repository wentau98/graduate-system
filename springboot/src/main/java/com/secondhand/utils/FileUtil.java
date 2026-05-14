package com.secondhand.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {
//    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";

    public static String upload(MultipartFile file,String location,String type) throws IOException {
        File dir = new File(location);
        if (!dir.exists()) dir.mkdirs();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, fileName);
        file.transferTo(dest);

        return "/upload"+type + fileName;
    }
}