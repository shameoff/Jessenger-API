package ru.shameoff.jessenger.common.utils;

import org.springframework.data.domain.Sort;
import ru.shameoff.jessenger.common.sharedDto.SortingParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.capitalize;

/**
 * Класс, который должны наследовать все SortingDto, чтобы иметь возможность автоматически создавать список для Sort.by()
 */
public abstract class SortingDto {

    /**
     * Метод для получения порядка и направлений сортировки, которые пойдут в Sort.by()
     * <p></p>
     * Он использует рефлексию
     *
     * @return {@link List}<{@link Sort.Order}>
     */
    public List<Sort.Order> createSortingList() {
        Map<String, SortingParam> params = new HashMap<>();
        Class<?> sortingDtoClass = this.getClass(); // Получаем описание класса sortingDto
        Field[] sortingDtoFields = sortingDtoClass.getFields(); // Получаем все поля sortingDto
        for (Field field : sortingDtoFields) { // Теперь для каждого поля мы получаем его имя и пытаемся вызвать геттер
            String fieldName = field.getName();
            try {
                Method getter = sortingDtoClass.getMethod("get" + capitalize(fieldName));
                var param = (SortingParam) getter.invoke(this);
                if (param != null) {
                    params.put(fieldName, param);
                }
            } catch (Exception e) {
                throw new RuntimeException("Ошибка во время попытки обработать sortingDto. Подробности: " + e);
            }
        }
        /* Все поля с их значениями теперь нужно преобразовать в список,
           для этого нам нужно получить направление сортировки и название поля, по которому сортируем,
           а затем создать объект типа <Sort.Order>
         */
        List<Sort.Order> sorting = params.entrySet().stream().map(entry -> {
            var fieldName = entry.getKey();
            var direction = entry.getValue().getDirection();
            return direction == SortingDirection.ASC ? Sort.Order.asc(fieldName) : Sort.Order.desc(fieldName);
        }).collect(Collectors.toList());
        // Так как для нас важно не только направление сортировки, но и порядок, сортируем по приоритету, его можно получить из словаря
        sorting.sort(Comparator.comparing(field -> params.get(field).getPriority()));
        return sorting;
    }
}
