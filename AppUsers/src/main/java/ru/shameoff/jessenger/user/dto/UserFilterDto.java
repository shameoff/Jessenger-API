package ru.shameoff.jessenger.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserFilterDto {

    private String fullName;
    private String username;
    private String city;
    private Integer lowerAge;
    private Integer upperAge;

}
