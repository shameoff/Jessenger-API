package ru.shameoff.jessenger.common.sharedDto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "0", description = "Номер страницы. Нумерация с 0. если поле пустое - вызывается первая страница")
    private Integer page;
    @Schema(example = "10", description = "Количество элементов на странице")
    private Integer pageSize;
}
