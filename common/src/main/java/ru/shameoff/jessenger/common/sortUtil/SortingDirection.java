package ru.shameoff.jessenger.common.sortUtil;

import lombok.Getter;

/**
 * Направление сортировки. Используется в {@link SortingDto}.
 */
@Getter
public enum SortingDirection {
    ASC("Сортировка по возрастанию", "ASCENDING"),
    DESC("Сортировка по убыванию", "DESCENDING");

    private final String description;
    private final String fullName;
    private SortingDirection(String description, String fullName) {
        this.description = description;
        this.fullName = fullName;
    }

}
