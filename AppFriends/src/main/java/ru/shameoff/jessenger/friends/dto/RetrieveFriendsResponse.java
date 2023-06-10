package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.shameoff.jessenger.common.sharedDto.PaginationDto;

@Data
@AllArgsConstructor
public class RetrieveFriendsResponse {
        private FriendDto[] friends;
        private PaginationDto pagination;
}
