package com.zource.service.storage;/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */


import com.zource.model.Category;
import com.zource.repository.CategoryRepository;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${file.upload.rootPath}")
    private String rootPath;

    public String uploadBrandLogo(MultipartFile file, Integer id) {

        String uploadedFileName = "";

        String uploadRootPath = rootPath + "/images/brands/" + id + "/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);


        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file", "/admin/brand?id="+id);
        }
/*

        Tika tika = new Tika();
        String detectedType = tika.detect(file.getOriginalFilename());
        System.out.println(detectedType);
*/


        // Client File Name
        String name = file.getOriginalFilename();
        System.out.println("Client File Name = " + name);

        if (name != null && name.length() > 0) {
            try {
                FileUtils.cleanDirectory(uploadRootDir);   // delete all files in the directory

                // Create the file at server
                File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();

                uploadedFileName = serverFile.getName();
                System.out.println("Write file: " + serverFile);

            } catch (IOException e) {
                String msg = String.format("Failed to store file", file.getName());
                throw new StorageException(msg, e);
            }
        }


        return uploadedFileName;
    }


    public void updateCategoryLogo(MultipartFile file, Category category) {

        String uploadRootPath = rootPath + "/images/categories/" + category.getId() + "/";
        File uploadRootDir = new File(uploadRootPath);

        if (category == null) {
            throw new StorageException("Category is null", "/admin/category?id="+category.getId());
        }


        if (file.isEmpty()) {
            throw new StorageException("Please select file to upload", "/admin/category?id="+category.getId());
        }

    /*    Tika tika = new Tika();
        String detectedType = tika.detect(file.getOriginalFilename());
        System.out.println(detectedType);*/

        uploadFile(file, uploadRootDir, category);
    }

    private void uploadFile(MultipartFile uploadFile, File uploadDir, Category category) {

        String uploadedFileName;

        // Client File Name
        String name = uploadFile.getOriginalFilename();
        //System.out.println("Client File Name = " + name);

        if (name != null && name.length() > 0) {
            try {
                FileUtils.cleanDirectory(uploadDir);   // delete all files in the directory

                // Create the file at server
                File serverFile = new File(uploadDir.getAbsolutePath() + File.separator + name);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(uploadFile.getBytes());
                stream.close();

                uploadedFileName = serverFile.getName();

                category.setLogoFileName(uploadedFileName);
                categoryRepository.save(category);

            } catch (IOException e) {
                String msg = String.format("Failed to store file", uploadFile.getName());
                throw new StorageException(msg, e);
            }
        }


    }
}
