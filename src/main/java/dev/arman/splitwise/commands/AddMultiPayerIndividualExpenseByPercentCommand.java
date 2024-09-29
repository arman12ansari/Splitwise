package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.ExpenseController;
import dev.arman.splitwise.dtos.MultiPayerIndividualRequestDto;
import dev.arman.splitwise.dtos.MultiPayerIndividualResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.arman.splitwise.commands.CommandKeyword.MULTI_PAY;

/**
 * @author mdarmanansari
 */
@Component
public class AddMultiPayerIndividualExpenseByPercentCommand implements Command {
    //u1 Expense u2 u3 MultiPay 500 300 200 Percent 20 30 50 Desc Netflix subscription
    private final ExpenseController expenseController;

    public AddMultiPayerIndividualExpenseByPercentCommand(ExpenseController expenseController) {
        this.expenseController = expenseController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        int indexOfMultiPay = inputWords.indexOf(MULTI_PAY);
        int indexOfPercent = inputWords.indexOf(CommandKeyword.PERCENT);

        return inputWords.size() > 10 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.EXPENSE) &&
                indexOfMultiPay != -1 && indexOfPercent != -1;
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        int indexOfMultiPay = inputWords.indexOf(MULTI_PAY);
        int indexOfDesc = inputWords.indexOf(CommandKeyword.DESC);
        int indexOfPercent = inputWords.indexOf(CommandKeyword.PERCENT);

        Long createById = Long.parseLong(inputWords.get(0));
        List<Long> paidUserId = new ArrayList<>();
        paidUserId.add(Long.parseLong(inputWords.get(0)));

        for (int i = 2; i < indexOfMultiPay; i++) {
            paidUserId.add(Long.parseLong(inputWords.get(i)));
        }

        List<Integer> paidAmount = new ArrayList<>();
        for (int i = indexOfMultiPay + 1; i < indexOfPercent; i++) {
            paidAmount.add(Integer.parseInt(inputWords.get(i)));
        }

        List<Integer> percent = new ArrayList<>();
        for (int i = indexOfPercent + 1; i < indexOfDesc; i++) {
            percent.add(Integer.parseInt(inputWords.get(i)));
        }

        StringBuilder description = new StringBuilder();
        for (int i = indexOfDesc + 1; i < inputWords.size(); i++) {
            description.append(inputWords.get(i)).append(" ");
        }

        MultiPayerIndividualRequestDto request = new MultiPayerIndividualRequestDto();

        request.setCreateById(createById);
        request.setPaidByUserIds(paidUserId);
        request.setAmountPaid(paidAmount);
        request.setPercent(percent);
        request.setDescription(description.toString().trim());

        MultiPayerIndividualResponseDto response = expenseController.addMultiPayerIndividualExpenseByPercent(request);

        System.out.println(response.getStatus());
        System.out.println(response.getMessage());

    }
}
