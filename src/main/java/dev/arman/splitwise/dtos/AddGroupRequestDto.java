package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class AddGroupRequestDto {
    private Long userId;
    private String groupName;
    private String groupDescription;
}
