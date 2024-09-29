package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.UserExpenseController;
import dev.arman.splitwise.dtos.ViewTotalRequestDto;
import dev.arman.splitwise.dtos.ViewTotalResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class ViewTotalCommand implements Command {
    //u1 MyTotal
    private final UserExpenseController userExpenseController;

    public ViewTotalCommand(UserExpenseController userExpenseController) {
        this.userExpenseController = userExpenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 2 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.MY_TOTAL);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long userId = Long.parseLong(inputWords.get(0));

        ViewTotalRequestDto request = new ViewTotalRequestDto();
        request.setUserId(userId);

        ViewTotalResponseDto response = userExpenseController.viewTotal(request);
        System.out.println(response.getStatus());
        System.out.println(response.getMessage());
        System.out.println("Total Amount = " + response.getAmount());
    }
}
