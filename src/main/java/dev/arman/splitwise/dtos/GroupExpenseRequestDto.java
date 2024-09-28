package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class GroupExpenseRequestDto {
    private Long groupId;
    private Long paidUserId;
    private String description;
    private int amount;
}
