package ru.shameoff.jessenger.common.sharedDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Используется в запросах с пагинацией как объект для управления пагинацией
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private Integer page;
    private Integer pageSize;
}
