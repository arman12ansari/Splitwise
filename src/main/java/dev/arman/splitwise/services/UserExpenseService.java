package dev.arman.splitwise.services;

import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.models.UserExpense;
import dev.arman.splitwise.models.UserExpenseType;
import dev.arman.splitwise.repositories.UserExpenseRepository;
import org.springframework.stereotype.Service;

/**
 * @author mdarmanansari
 */
@Service
public class UserExpenseService {
    private final UserExpenseRepository userExpenseRepository;

    public UserExpenseService(UserExpenseRepository userExpenseRepository) {
        this.userExpenseRepository = userExpenseRepository;
    }

    public void paidUserExpense(User user, Expense expense, int amount) {
        UserExpense userExpense = new UserExpense();
        userExpense.setUser(user);
        userExpense.setExpense(expense);
        userExpense.setAmount(amount);
        userExpense.setUserExpenseType(UserExpenseType.PAID);

        userExpenseRepository.save(userExpense);
    }

    public void hadToPayUserExpense(User user, Expense expense, int amount) {
        UserExpense userExpense = new UserExpense();
        userExpense.setUser(user);
        userExpense.setExpense(expense);
        userExpense.setAmount(amount);
        userExpense.setUserExpenseType(UserExpenseType.HAD_TO_PAY);

        userExpenseRepository.save(userExpense);
    }
}
