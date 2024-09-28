package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class IndividualExpenseResponseDto {
    private Long expenseId;
    private String status;
    private String message;
}
