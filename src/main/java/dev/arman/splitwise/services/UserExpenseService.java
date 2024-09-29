package dev.arman.splitwise.services;

import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.models.UserExpense;
import dev.arman.splitwise.models.UserExpenseType;
import dev.arman.splitwise.repositories.UserExpenseRepository;
import dev.arman.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service
public class UserExpenseService {
    private final UserExpenseRepository userExpenseRepository;
    private final UserRepository userRepository;

    public UserExpenseService(UserExpenseRepository userExpenseRepository, UserRepository userRepository) {
        this.userExpenseRepository = userExpenseRepository;
        this.userRepository = userRepository;
    }

    public List<UserExpense> viewHistory(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(optionalUser.get());

        return userExpenses;
    }

    public int viewTotal(Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(optionalUser.get());

        int amount = 0;

        for (UserExpense userExpense : userExpenses) {
            if (userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                amount += userExpense.getAmount();
            } else {
                amount -= userExpense.getAmount();
            }
        }

        return amount;
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
