package org.thuanthanhtech.mymuseummanagement.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thuanthanhtech.mymuseummanagement.dto.Response;
import org.thuanthanhtech.mymuseummanagement.service.impl.FileStorageServiceImpl;

@RestController
public class FileUploadApi {

    @Autowired
    private FileStorageServiceImpl fileStorageServiceImpl;

    @PostMapping("/upload-file")
    public Response uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageServiceImpl.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download-file/")
                .path(fileName)
                .toUriString();

        return new Response(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/upload-multiple-files")
    public List < Response > uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }
}