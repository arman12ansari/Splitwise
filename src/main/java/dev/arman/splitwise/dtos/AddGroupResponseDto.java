package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class AddGroupResponseDto {
    private long groupId;
    private String status;
    private String message;
}
