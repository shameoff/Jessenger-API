package ru.shameoff.jessenger.common.sortUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Используется как тип данных в {@link SortingDto}.
 * Также является одной базовой ячейкой для передачи параметров сортировки в запросе
 */
@Data
public class SortingParam {
    @Schema(example = "1", description = "Приоритет сортировки. Чем меньше, тем выше приоритет")
    private Integer priority;
    @Schema(example = "ASC", description = "Направление сортировки. Доступные значения: ASC, DESC")
    private SortingDirection direction;

}
