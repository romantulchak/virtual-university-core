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

    private FileUploader(){

    }

    /**
     * Upload file to directory
     * @param file - MultipartFile
     * @param path path to directory
     * @param directory name of directory only NAME
     * @param fileName use static method generateNameForFile() to generate
     *                 unique name
     * @return Path to file with localhost
     */
    public static String uploadFile(MultipartFile file, String path, String filePath, String directory, String fileName){
        if(file != null){
            File dir = new File(path + directory);
            if(!dir.exists()){
                dir.mkdir();
            }
            try {
                Path copyLocation = Paths.get(filePath);
                Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
                return "http://localhost:8080/" + "files/" + directory + "/" + fileName;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        throw new RuntimeException("File not found");
    }

    public static String getLocalPathToFile(String path, String directory, String fileName){
        return path + "/" + directory + "/" + fileName;
    }

    /**
     * Generate random name for file
     * @param originalName File name
     * @return unique name for file
     */
    public static String generateNameForFile(String originalName){
       return UUID.randomUUID().toString().substring(0, 10) + "-" + originalName.replace(" ","-");
    }
}
