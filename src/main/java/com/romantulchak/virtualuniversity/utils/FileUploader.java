package com.romantulchak.virtualuniversity.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploader {


    /**
     *
     * @param file - MultipartFile
     * @param path path to directory
     * @param directory name of directory only NAME
     * @return Path to file with localhost
     */
    public static String uploadFile(MultipartFile file, String path, String directory){
        if(file != null){
            File dir = new File(path + directory);
            if(!dir.exists()){
                dir.mkdir();
            }
            try {
                String fileOriginalName = UUID.randomUUID() + "." + file.getOriginalFilename().replace(" ","-");
                String filePath = path + "/" + directory + "/" + fileOriginalName;
                Path copyLocation = Paths.get(filePath);
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                return "http://localhost:8080/" + "files/" + directory + "/" + fileOriginalName;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("File not found");
    }
}
