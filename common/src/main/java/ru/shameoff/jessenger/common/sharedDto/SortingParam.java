package ru.shameoff.jessenger.common.sharedDto;

import lombok.Data;
import ru.shameoff.jessenger.common.utils.SortingDirection;

/**
 * Используется как тип данных в любой SortingDto
 */
@Data
public class SortingParam {
    private Integer priority;
    private SortingDirection direction;

}
