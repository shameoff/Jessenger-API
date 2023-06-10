package ru.shameoff.jessenger.user.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;

@Data
public class RetrieveUsersRequest {

    @JsonSetter(nulls = Nulls.SKIP)
    private UserFilterDto filters;

    @JsonSetter(nulls = Nulls.SKIP)
    private PaginationDto pagination;

    @JsonSetter(nulls = Nulls.SKIP)
    private UserSortingDto sorting;
}
