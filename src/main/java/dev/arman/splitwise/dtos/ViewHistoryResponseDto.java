package dev.arman.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Getter
@Setter
public class ViewHistoryResponseDto {
    private List<Long> expenseId;
    private List<Integer> amount;
    private List<String> description;
    private List<String> expenseType;
    private List<String> userExpenseType;
    private String status;
    private String message;
}
