package ru.shameoff.jessenger.friends.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetrieveFriendsDto {

    private String filter;
    private String paginationData;
}
