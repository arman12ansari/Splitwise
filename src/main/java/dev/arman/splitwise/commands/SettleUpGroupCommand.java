package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.ExpenseController;
import dev.arman.splitwise.dtos.SettleUpGroupRequestDto;
import dev.arman.splitwise.dtos.SettleUpGroupResponseDto;
import dev.arman.splitwise.services.strategies.settleUpStrategy.Transaction;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class SettleUpGroupCommand implements Command {
    //u1 SettleUp g1
    private final ExpenseController expenseController;

    public SettleUpGroupCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 3 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.SETTLE_UP);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long groupId = Long.parseLong(inputWords.get(2));

        SettleUpGroupRequestDto request = new SettleUpGroupRequestDto();
        request.setGroupId(groupId);

        SettleUpGroupResponseDto response = expenseController.settleUpGroup(request);
        System.out.println(response.getStatus());
        System.out.println(response.getMessage());
        System.out.println("Transactions List for Group: " + groupId);

        int counter = 1;
        for (Transaction transaction : response.getTransactions()) {
            System.out.println("Transaction " + counter + ":");
            System.out.println("From: " + transaction.getFrom().getId());
            System.out.println("To: " + transaction.getTo().getId());
            System.out.println("Amount: " + transaction.getAmount());
            counter++;
        }

    }
}
