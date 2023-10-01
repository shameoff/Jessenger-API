package ru.shameoff.jessenger.fileserver.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shameoff.jessenger.fileserver.MinioConfig;

import java.io.ByteArrayInputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MinioFileService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;


    public UUID upload(byte[] content) {
        var id = UUID.randomUUID();
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(id.toString())
                    .stream(new ByteArrayInputStream(content), content.length, -1)
                    .build());
            return id;
        } catch (Exception e) {
            throw new RuntimeException("Факир был пьян и фокус не загрузился по причине: " + e);
        }
    }

    public byte[] download(UUID id) {
        var args = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(id.toString())
                .build();
        try (var in = minioClient.getObject(args)) {
            return in.readAllBytes();

        } catch (Exception e) {
            throw new RuntimeException("Факир был пьян и фокус не скачался по причине: " + e);
        }
    }
}
