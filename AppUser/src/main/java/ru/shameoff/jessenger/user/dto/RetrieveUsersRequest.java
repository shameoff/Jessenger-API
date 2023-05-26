package ru.shameoff.jessenger.user.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;

import java.util.Optional;

@Data
public class RetrieveUsersRequest {

    @JsonSetter(nulls = Nulls.SKIP)

    private UserFilterDto filterDto;
    @JsonSetter(nulls = Nulls.SKIP)

    private PaginationDto paginationDto;
    @JsonSetter(nulls = Nulls.SKIP)

    private UserSortingDto sortingDto;
}
