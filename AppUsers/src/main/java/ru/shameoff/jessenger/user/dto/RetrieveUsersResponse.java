package ru.shameoff.jessenger.user.dto;

import lombok.Builder;
import lombok.Data;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;
import ru.shameoff.jessenger.common.sharedDto.UserDto;

import java.util.List;

@Data
@Builder
public class RetrieveUsersResponse {
    private List<UserDto> users;
    private PaginationDto pagination;
    private UserSortingDto sorting;
}
