package dev.arman.splitwise.services.strategies.settleUpStrategy;

import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.models.UserExpense;
import dev.arman.splitwise.models.UserExpenseType;
import dev.arman.splitwise.repositories.UserExpenseRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author mdarmanansari
 */
@Component("twoSetsSettleUpStrategy")
public class TwoSetsSettleUpStrategy implements SettleUpStrategy {
    private final UserExpenseRepository userExpenseRepository;

    public TwoSetsSettleUpStrategy(UserExpenseRepository userExpenseRepository) {
        this.userExpenseRepository = userExpenseRepository;
    }

    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        List<UserExpense> allUserExpenseForTheseExpenses = userExpenseRepository.findAllByExpenseIn(expenses);

        HashMap<User, Integer> moneyPaidExtra = new HashMap<>();

        for (UserExpense userExpense : allUserExpenseForTheseExpenses) {
            User user = userExpense.getUser();
            int currentExtraPaid = 0;

            if (moneyPaidExtra.containsKey(user)) {
                currentExtraPaid = moneyPaidExtra.get(user);
            }

            if (userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                moneyPaidExtra.put(user, currentExtraPaid + userExpense.getAmount());
            } else {
                moneyPaidExtra.put(user, currentExtraPaid - userExpense.getAmount());
            }
        }


        TreeSet<ExpensePair> lessPaid = new TreeSet<>(Comparator.comparingInt(ExpensePair::getAmount));
        TreeSet<ExpensePair> extraPaid = new TreeSet<>(Comparator.comparingInt(ExpensePair::getAmount));

        for (Map.Entry<User, Integer> userAmount : moneyPaidExtra.entrySet()) {
            if (userAmount.getValue() < 0) {
                lessPaid.add(new ExpensePair(userAmount.getKey(), userAmount.getValue()));
            } else {
                extraPaid.add(new ExpensePair(userAmount.getKey(), userAmount.getValue()));
            }
        }

        List<Transaction> transactions = new ArrayList<>();

        while (!lessPaid.isEmpty()) {
            ExpensePair lessPaidPair = lessPaid.pollFirst();
            ExpensePair extraPaidPair = extraPaid.pollFirst();

            Transaction t = new Transaction();

            if (lessPaidPair != null && extraPaidPair != null) {
                t.setFrom(lessPaidPair.getUser());
                t.setTo(extraPaidPair.getUser());

                if (Math.abs(lessPaidPair.getAmount()) < extraPaidPair.getAmount()) {
                    t.setAmount(Math.abs(lessPaidPair.getAmount()));

                    int i = extraPaidPair.getAmount() - Math.abs(lessPaidPair.getAmount());
                    if (!(i == 0)) {
                        extraPaid.add(new ExpensePair(extraPaidPair.getUser(), i));
                    }
                } else {
                    t.setAmount(extraPaidPair.getAmount());

                    int i = lessPaidPair.getAmount() + extraPaidPair.getAmount();
                    if (!(i == 0)) {
                        lessPaid.add(new ExpensePair(lessPaidPair.getUser(), i));
                    }
                }
            }
            transactions.add(t);
        }

        return transactions;
    }
}

@Getter
@Setter
class ExpensePair {
    private User user;
    private Integer amount;

    public ExpensePair(User user, Integer amount) {
        this.user = user;
        this.amount = amount;
    }
}
