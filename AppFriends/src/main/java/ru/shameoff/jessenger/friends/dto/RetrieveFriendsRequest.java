package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;

@Data
@AllArgsConstructor
public class RetrieveFriendsRequest {
    private FriendsFilterDto filterDto;
    private PaginationDto pagination;
}
