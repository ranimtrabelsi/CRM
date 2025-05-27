package org.taskspfe.pfe.service.file;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.file.FileData;
import org.taskspfe.pfe.repository.FileDataRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements  FileService{

    private final FileDataRepository fileDataRepository;


    public FileServiceImpl(FileDataRepository fileDataRepository)
    {
        this.fileDataRepository = fileDataRepository;
    }

    public FileData save(FileData fileData)
    {
        return fileDataRepository.save(fileData);
    }

    @Transactional
    public void deleteFileById(final long fileId)
    {
        FileData fileToDelete = getFileDataById(fileId);
        fileDataRepository.deleteFileDataById(fileId);
    }

    private final String  FILE_SYSTEM_PATH= Paths.get("").toAbsolutePath().resolve("src").resolve("main").resolve("resources").resolve("images").toString() + "/";

    @Override
    public FileData processUploadedFile(@NotNull final MultipartFile file) throws IOException {
        var originalFileName = file.getOriginalFilename();
        var fileName = originalFileName.substring(0, originalFileName.indexOf('.'));
        var extension = originalFileName.substring(originalFileName.indexOf('.'));
        var filePath = FILE_SYSTEM_PATH + fileName + UUID.randomUUID() + extension;

        FileData fileData = FileData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build();
        fileDataRepository.save(fileData);
        file.transferTo(new File(filePath));
        return fileData;
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileData fileData) throws IOException {
        final String filePath = fileData.getFilePath();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        HttpHeaders headers = new HttpHeaders();
        String contentType = determineContentType(filePath);
        headers.setContentDispositionFormData("attachment", fileData.getFilePath());
        headers.setContentType(MediaType.parseMediaType(contentType));

        return new ResponseEntity<>(file, headers, HttpStatus.OK);
    }


    @Override
    @Transactional
    public void deleteFileFromFileSystem(@NotNull final FileData fileData) throws IOException {
        File fileToDelete = new File(fileData.getFilePath());
        if(!fileToDelete.delete())
        {
            throw new IOException(String.format("Failed to delete file with ID : %d",fileData.getId()));
        }
        fileDataRepository.deleteFileDataById(fileData.getId());
    }

    @Override
    @Transactional
    public void deleteAllFiles(@NotNull final List<FileData> files) throws IOException {
        for(var file : files)
        {
            File fileToDelete = new File(file.getFilePath());
            if(!fileToDelete.delete())
            {
                throw new IOException(String.format("Failed to delete file with file path : %s",file.getFilePath()));
            }
        }
        fileDataRepository.deleteAllFiles(files);
    }
    public String determineContentType(@NotNull String filePath) {

        String extension = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();

        HashMap<String, String> extensionToContentTypeMap = new HashMap<>();
        extensionToContentTypeMap.put("png", "image/png");
        extensionToContentTypeMap.put("jpg", "image/jpeg");
        extensionToContentTypeMap.put("jpeg", "image/jpeg");
        extensionToContentTypeMap.put("gif", "image/gif");
        extensionToContentTypeMap.put("bmp", "image/bmp");
        extensionToContentTypeMap.put("ico", "image/vnd.microsoft.icon");
        extensionToContentTypeMap.put("tiff", "image/tiff");
        return extensionToContentTypeMap.getOrDefault(extension, "application/octet-stream");
    }
    public FileData getFileDataById(long fileDataId)
    {
        return fileDataRepository.fetchFileDataById(fileDataId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The file with ID : %s could not be found.", fileDataId)));
    }
}