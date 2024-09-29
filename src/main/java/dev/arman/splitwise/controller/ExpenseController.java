package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.*;
import dev.arman.splitwise.exceptions.GroupNotFoundException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.services.ExpenseService;
import dev.arman.splitwise.services.strategies.settleUpStrategy.Transaction;
import org.springframework.stereotype.Controller;

import java.util.List;

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
        } catch (GroupNotFoundException | UserNotFoundException groupNotFoundException) {
            groupExpenseResponseDto.setStatus("FAILURE");
            groupExpenseResponseDto.setMessage(groupNotFoundException.getMessage());
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
        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());
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
        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());
        }

        return response;
    }

    public SettleUpUserResponseDto settleUpUser(SettleUpUserRequestDto request) {
        List<Transaction> transactions;
        SettleUpUserResponseDto response = new SettleUpUserResponseDto();

        try {
            transactions = expenseService.settleUpUser(request.getUserId());
            response.setStatus("SUCCESS");
            response.setMessage("List of Transaction to settle up user fetched successfully");
            response.setTransactions(transactions);
        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());
        }

        return response;
    }

    public SettleUpGroupResponseDto settleUpGroup(SettleUpGroupRequestDto request) {
        List<Transaction> transactions;
        SettleUpGroupResponseDto response = new SettleUpGroupResponseDto();

        try {
            transactions = expenseService.settleUpGroup(request.getGroupId());
            response.setStatus("SUCCESS");
            response.setMessage("List of Transaction to settle up group fetched successfully");
            response.setTransactions(transactions);
        } catch (GroupNotFoundException groupNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(groupNotFoundException.getMessage());
        }

        return response;
    }
}
