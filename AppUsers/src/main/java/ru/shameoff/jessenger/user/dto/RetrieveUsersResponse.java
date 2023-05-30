package ru.shameoff.jessenger.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;
import ru.shameoff.jessenger.common.sharedDto.UserDto;
import ru.shameoff.jessenger.user.entity.UserEntity;

import java.util.List;

@Data
@Builder
public class RetrieveUsersResponse {
    private List<UserDto> users;
    private PaginationDto paginationDto;
    private UserSortingDto sortingDto;
}
