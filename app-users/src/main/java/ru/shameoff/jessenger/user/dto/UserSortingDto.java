package ru.shameoff.jessenger.user.dto;

import lombok.Data;
import ru.shameoff.jessenger.common.sortUtil.SortingParam;
import ru.shameoff.jessenger.common.sortUtil.SortingDto;

@Data
public class UserSortingDto extends SortingDto {


    private SortingParam fullName;
    private SortingParam username;
    private SortingParam city;
    private SortingParam birthDate;
}
