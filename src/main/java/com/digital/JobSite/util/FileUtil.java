package com.digital.JobSite.util;

import com.digital.JobSite.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    // TODO: Upload resume and return name of the filed being saved
    public static String saveResume(String path, MultipartFile file, String username) throws IOException {

        String filePath = path + File.separator + username;

        File nFile = new File(path);

        if(!nFile.exists()) {
            nFile.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return nFile.getName();
    }
}
