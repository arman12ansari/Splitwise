package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.GroupExpenseRequestDto;
import dev.arman.splitwise.dtos.GroupExpenseResponseDto;
import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.services.ExpenseService;
import org.springframework.stereotype.Controller;

/**
 * @author mdarmanansari
 */
@Controller
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public GroupExpenseResponseDto addGroupExpense(GroupExpenseRequestDto groupExpenseRequestDto) {
        Expense expense;
        GroupExpenseResponseDto groupExpenseResponseDto = new GroupExpenseResponseDto();

        try {
            expense = expenseService.addGroupExpense(groupExpenseRequestDto.getGroupId(),
                    groupExpenseRequestDto.getPaidUserId(), groupExpenseRequestDto.getDescription(),
                    groupExpenseRequestDto.getAmount());

            groupExpenseResponseDto.setExpenseId(expense.getId());
            groupExpenseResponseDto.setStatus("SUCCESS");
            groupExpenseResponseDto.setMessage("Group expense added successfully");
        } catch (Exception e) {
            groupExpenseResponseDto.setStatus("FAILURE");
            groupExpenseResponseDto.setMessage("Failed to add group expense");
        }

        return groupExpenseResponseDto;
    }
}
