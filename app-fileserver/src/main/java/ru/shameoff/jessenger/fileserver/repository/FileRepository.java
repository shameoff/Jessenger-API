package ru.shameoff.jessenger.fileserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shameoff.jessenger.fileserver.entity.FileEntity;

import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID>{

    public FileEntity findByFileId(UUID id);
}
