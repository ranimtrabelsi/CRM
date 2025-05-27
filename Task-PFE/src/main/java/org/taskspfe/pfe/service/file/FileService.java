package org.taskspfe.pfe.service.file;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.taskspfe.pfe.model.file.FileData;

import java.io.IOException;
import java.util.List;

public interface FileService {
    public FileData processUploadedFile(@NotNull final MultipartFile file) throws IOException;
    public void deleteAllFiles(@NotNull final List<FileData> files) throws IOException;
    public String determineContentType(@NotNull String filePath);
    public FileData getFileDataById(long fileDataId);
    public ResponseEntity<byte[]> downloadFile(@NotNull final  FileData fileData) throws IOException ;
    public void deleteFileFromFileSystem(@NotNull final FileData fileData) throws IOException ;
}
