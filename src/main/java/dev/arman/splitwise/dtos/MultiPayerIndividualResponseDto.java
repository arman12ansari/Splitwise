package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class MultiPayerIndividualResponseDto {
    private Long expenseId;
    private String status;
    private String message;
}
