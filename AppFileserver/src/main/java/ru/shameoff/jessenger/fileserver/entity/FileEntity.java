package ru.shameoff.jessenger.fileserver.entity;

import lombok.Data;
import ru.shameoff.jessenger.common.BaseEntity;

import javax.persistence.Entity;
import java.util.UUID;

@Data
@Entity

public class FileEntity extends BaseEntity {

    private String name;
    private UUID fileId;
    private Long size;
}
