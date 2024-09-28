package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */

@Getter
@Setter
public class RegisterUserResponseDto {
    private long userId;
    private String status;
    private String message;
}
