package ru.shameoff.javalab1.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CreateUpdateUserDto {

    private String login;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
}
