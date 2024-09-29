package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.ExpenseController;
import dev.arman.splitwise.dtos.SettleUpUserRequestDto;
import dev.arman.splitwise.dtos.SettleUpUserResponseDto;
import dev.arman.splitwise.services.strategies.settleUpStrategy.Transaction;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class SettleUpUserCommand implements Command {
    //u1 SettleUp
    private final ExpenseController expenseController;

    public SettleUpUserCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 2 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.SETTLE_UP);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long userId = Long.parseLong(inputWords.get(0));

        SettleUpUserRequestDto request = new SettleUpUserRequestDto();
        request.setUserId(userId);

        SettleUpUserResponseDto response = expenseController.settleUpUser(request);
        System.out.println(response.getStatus());
        System.out.println(response.getMessage());
        System.out.println("Transactions List for User: " + userId);

        int counter = 1;
        for (Transaction transaction : response.getTransactions()) {
            System.out.println("Transaction " + counter + ":");
            System.out.println("From: " + transaction.getFrom().getId());
            System.out.println("To: " + transaction.getTo().getId());
            System.out.println("Amount: " + transaction.getAmount());
        }
    }
}
