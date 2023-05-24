package ru.shameoff.jessenger.fileserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shameoff.jessenger.fileserver.service.FileService;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    public final FileService fileService;

    @SneakyThrows
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UUID upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        return fileService.upload(file.getBytes(), file.getOriginalFilename(), file.getSize());
    }

    @SneakyThrows
    @GetMapping(value = "/download/{id}", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable UUID id) {
        return fileService.download(id);
    }

}
