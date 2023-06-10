package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginationData {
    private Integer page;
    private Integer pageSize;
}
