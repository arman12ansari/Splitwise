package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */

@Getter
@Setter
public class RegisterUserRequestDto {
    private String userName;
    private String password;
    private String phoneNumber;
}
