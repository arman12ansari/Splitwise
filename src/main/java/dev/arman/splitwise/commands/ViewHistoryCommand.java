package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.UserExpenseController;
import dev.arman.splitwise.dtos.ViewHistoryRequestDto;
import dev.arman.splitwise.dtos.ViewHistoryResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class ViewHistoryCommand implements Command {
    //u1 History
    private final UserExpenseController userExpenseController;

    public ViewHistoryCommand(UserExpenseController userExpenseController) {
        this.userExpenseController = userExpenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 2 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.HISTORY);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long userId = Long.parseLong(inputWords.get(0));

        ViewHistoryRequestDto request = new ViewHistoryRequestDto();
        request.setUserId(userId);

        ViewHistoryResponseDto response = userExpenseController.viewHistory(request);
        System.out.println(response.getStatus());
        System.out.println(response.getMessage());

        int size = response.getAmount().size();

        for (int i = 0; i < size; i++) {
            System.out.println("Expense ID: " + response.getExpenseId().get(i));
            System.out.println("Amount: " + response.getAmount().get(i));
            System.out.println("Description: " + response.getDescription().get(i));
            System.out.println("Expense Type: " + response.getExpenseType().get(i));
            System.out.println("User Expense Type: " + response.getUserExpenseType().get(i));
        }
    }
}
