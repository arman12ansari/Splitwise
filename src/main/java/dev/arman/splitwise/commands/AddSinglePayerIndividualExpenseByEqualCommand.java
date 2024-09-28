package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.ExpenseController;
import dev.arman.splitwise.dtos.IndividualExpenseRequestDto;
import dev.arman.splitwise.dtos.IndividualExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.arman.splitwise.commands.CommandKeyword.SELF_PAY;

/**
 * @author mdarmanansari
 */
@Component
public class AddSinglePayerIndividualExpenseByEqualCommand implements Command {
    //u1 Expense u2 u3 u4 iPay 1000 Equal Desc Last night dinner
    private final ExpenseController expenseController;

    public AddSinglePayerIndividualExpenseByEqualCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        int indexOfSelfPay = inputWords.indexOf(SELF_PAY);
        int indexOfEqual = inputWords.indexOf(CommandKeyword.EQUAL);

        return inputWords.size() > 7 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.EXPENSE) &&
                indexOfEqual != -1 && indexOfSelfPay != -1;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        int indexOfSelfPay = inputWords.indexOf(SELF_PAY);
        int indexOfDesc = inputWords.indexOf(CommandKeyword.DESC);

        Long paidUserId = Long.parseLong(inputWords.get(0));
        int amount = Integer.parseInt(inputWords.get(indexOfSelfPay + 1));
        StringBuilder description = new StringBuilder();
        for (int i = indexOfDesc + 1; i < inputWords.size(); i++) {
            description.append(inputWords.get(i)).append(" ");
        }

        List<Long> owedUserIds = new ArrayList<>();
        for (int i = 2; i < indexOfSelfPay; i++) {
            owedUserIds.add(Long.parseLong(inputWords.get(i)));
        }

        IndividualExpenseRequestDto request = new IndividualExpenseRequestDto();
        request.setPaidUserId(paidUserId);
        request.setOwedUserIds(owedUserIds);
        request.setAmount(amount);
        request.setDescription(description.toString().trim());
        request.setTypeOfOperation(CommandKeyword.EQUAL);

        IndividualExpenseResponseDto response =
                expenseController.addSinglePayerIndividualExpense(request);

        System.out.println(response.getStatus());
        System.out.println(response.getMessage());
    }
}
