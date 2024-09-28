package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.GroupExpenseRequestDto;
import dev.arman.splitwise.dtos.GroupExpenseResponseDto;
import dev.arman.splitwise.dtos.IndividualExpenseRequestDto;
import dev.arman.splitwise.dtos.IndividualExpenseResponseDto;
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

    public IndividualExpenseResponseDto addSinglePayerIndividualExpense(IndividualExpenseRequestDto requestDto) {
        Expense expense;
        IndividualExpenseResponseDto response = new IndividualExpenseResponseDto();

        try {
            expense = expenseService.addSinglePayerIndividualExpense(requestDto.getPaidUserId(),
                    requestDto.getOwedUserIds(), requestDto.getAmount(), requestDto.getDescription(),
                    requestDto.getTypeOfOperation());

            response.setExpenseId(expense.getId());
            response.setStatus("SUCCESS");
            response.setMessage("Individual expense added successfully");
        } catch (Exception e) {
            response.setStatus("FAILURE");
            response.setMessage("Failed to add individual expense");
        }

        return response;
    }
}
