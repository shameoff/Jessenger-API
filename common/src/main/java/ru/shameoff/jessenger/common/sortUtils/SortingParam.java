package ru.shameoff.jessenger.common.sortUtils;

import lombok.Data;

/**
 * Используется как тип данных в {@link SortingDto}.
 * Также является одной базовой ячейкой для передачи параметров сортировки в запросе
 */
@Data
public class SortingParam {
    private Integer priority;
    private SortingDirection direction;

}
