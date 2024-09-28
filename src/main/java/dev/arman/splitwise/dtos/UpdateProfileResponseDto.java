package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class UpdateProfileResponseDto {
    private Long userId;
    private String status;
    private String message;
}
