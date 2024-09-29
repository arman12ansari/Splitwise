package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.*;
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

    public IndividualExpenseResponseDto addSinglePayerIndividualExpenseByEqual(IndividualExpenseRequestDto requestDto) {
        Expense expense;
        IndividualExpenseResponseDto response = new IndividualExpenseResponseDto();

        try {
            expense = expenseService.addSinglePayerIndividualExpenseByEqual(requestDto.getPaidUserId(),
                    requestDto.getOwedUserIds(), requestDto.getAmount(), requestDto.getDescription());

            response.setExpenseId(expense.getId());
            response.setStatus("SUCCESS");
            response.setMessage("Individual expense added successfully");
        } catch (Exception e) {
            response.setStatus("FAILURE");
            response.setMessage("Failed to add individual expense");
        }

        return response;
    }

    public MultiPayerIndividualResponseDto addMultiPayerIndividualExpenseByPercent(MultiPayerIndividualRequestDto requestDto) {
        Expense expense;
        MultiPayerIndividualResponseDto response = new MultiPayerIndividualResponseDto();

        try {
            expense = expenseService.addMultiPayerIndividualExpenseByPercent(requestDto.getCreateById(),
                    requestDto.getPaidByUserIds(), requestDto.getAmountPaid(), requestDto.getPercent(),
                    requestDto.getDescription());

            response.setExpenseId(expense.getId());
            response.setStatus("SUCCESS");
            response.setMessage("MultiPayer expense By Percent added successfully");
        } catch (Exception e) {
            response.setStatus("FAILURE");
            response.setMessage("Failed to add MultiPayer expense By Percent expense");
        }

        return response;
    }
}
