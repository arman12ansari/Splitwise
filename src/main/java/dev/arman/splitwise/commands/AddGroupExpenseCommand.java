package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.ExpenseController;
import dev.arman.splitwise.dtos.GroupExpenseRequestDto;
import dev.arman.splitwise.dtos.GroupExpenseResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static dev.arman.splitwise.commands.CommandKeyword.SELF_PAY;

/**
 * @author mdarmanansari
 */
@Component
public class AddGroupExpenseCommand implements Command {
    //u1 Expense g1 iPay 1000 Equal Desc Wifi Bill
    private final ExpenseController expenseController;

    public AddGroupExpenseCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() > 7 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.EXPENSE) &&
                inputWords.get(3).equalsIgnoreCase(SELF_PAY);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long paidUserId = Long.parseLong(inputWords.get(0));
        Long groupId = Long.parseLong(inputWords.get(2));

        int indexOfSelfPay = inputWords.indexOf(SELF_PAY);
        int amount = Integer.parseInt(inputWords.get(indexOfSelfPay + 1));

        int totalSize = inputWords.size();
        int indexOfDesc = inputWords.indexOf(CommandKeyword.DESC);
        StringBuilder description = new StringBuilder();

        for (int i = indexOfDesc + 1; i < totalSize; i++) {
            description.append(inputWords.get(i)).append(" ");
        }

        GroupExpenseRequestDto groupExpenseRequestDto = new GroupExpenseRequestDto();
        groupExpenseRequestDto.setPaidUserId(paidUserId);
        groupExpenseRequestDto.setGroupId(groupId);
        groupExpenseRequestDto.setAmount(amount);
        groupExpenseRequestDto.setDescription(description.toString().trim());

        GroupExpenseResponseDto response = expenseController.addGroupExpense(groupExpenseRequestDto);

        System.out.println(response.getStatus());
        System.out.println(response.getMessage());
    }
}
