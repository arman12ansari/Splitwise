package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.ViewHistoryRequestDto;
import dev.arman.splitwise.dtos.ViewHistoryResponseDto;
import dev.arman.splitwise.dtos.ViewTotalRequestDto;
import dev.arman.splitwise.dtos.ViewTotalResponseDto;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.UserExpense;
import dev.arman.splitwise.services.UserExpenseService;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Controller
public class UserExpenseController {
    private final UserExpenseService userExpenseService;

    public UserExpenseController(UserExpenseService userExpenseService) {
        this.userExpenseService = userExpenseService;
    }

    public ViewHistoryResponseDto viewHistory(ViewHistoryRequestDto request) {
        List<UserExpense> userExpense;
        ViewHistoryResponseDto response = new ViewHistoryResponseDto();
        response.setExpenseId(new ArrayList<>());
        response.setAmount(new ArrayList<>());
        response.setDescription(new ArrayList<>());
        response.setExpenseType(new ArrayList<>());
        response.setUserExpenseType(new ArrayList<>());

        try {
            userExpense = userExpenseService.viewHistory(request.getUserId());
            response.setStatus("SUCCESS");
            response.setMessage("User history fetched successfully");

            for (UserExpense userExpense1 : userExpense) {
                response.getExpenseId().add(userExpense1.getExpense().getId());
                response.getAmount().add(userExpense1.getAmount());
                response.getDescription().add(userExpense1.getExpense().getDescription());
                response.getExpenseType().add(userExpense1.getExpense().getExpenseType().toString());
                response.getUserExpenseType().add(userExpense1.getUserExpenseType().toString());
            }

        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());

        }

        return response;
    }

    public ViewTotalResponseDto viewTotal(ViewTotalRequestDto request) {
        int totalAmount;
        ViewTotalResponseDto response = new ViewTotalResponseDto();

        try {
            totalAmount = userExpenseService.viewTotal(request.getUserId());
            response.setStatus("SUCCESS");
            response.setMessage("Total amount fetched successfully for user " + request.getUserId());
            response.setAmount(totalAmount);

        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());

        }

        return response;
    }
}
