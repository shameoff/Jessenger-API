package ru.shameoff.jessenger.fileserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.fileserver.entity.FileEntity;
import ru.shameoff.jessenger.fileserver.repository.FileRepository;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioFileService minioFileService;
    private final FileRepository fileRepository;

    public UUID upload(byte[] content, String fileName, Long size) {
        var id = minioFileService.upload(content);
        var newRecord = new FileEntity();
        newRecord.setFileId(id);
        newRecord.setName(fileName);
        newRecord.setSize(size);
        fileRepository.save(newRecord);
        return id;
    }

    public ResponseEntity<byte[]> download(UUID id) {
        var file = fileRepository.findByFileId(id);
        if (Objects.isNull(file)) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getName());
        var content = minioFileService.download(id);
        return ResponseEntity.ok()
                .headers(headers)
                .body(content);
    }
}
