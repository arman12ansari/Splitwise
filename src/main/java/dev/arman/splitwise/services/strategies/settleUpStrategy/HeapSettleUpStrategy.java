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
@Component("heapSettleUpStrategy")
public class HeapSettleUpStrategy implements SettleUpStrategy {
    private final UserExpenseRepository userExpenseRepository;

    public HeapSettleUpStrategy(UserExpenseRepository userExpenseRepository) {
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

        Comparator<UserAmount> minHeapComparator = Comparator.comparingDouble(UserAmount::getAmount);
        Comparator<UserAmount> maxHeapComparator = Comparator.comparingDouble(UserAmount::getAmount).reversed();

        PriorityQueue<UserAmount> maxHeap = new PriorityQueue<>(maxHeapComparator);
        PriorityQueue<UserAmount> minHeap = new PriorityQueue<>(minHeapComparator);

        for (Map.Entry<User, Integer> entry : moneyPaidExtra.entrySet()) {
            if (entry.getValue() < 0) {
                minHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() > 0) {
                maxHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            } else {
                System.out.println("User " + entry.getKey().getName() + " has no dues");
            }
        }

        List<Transaction> settleUpTransactions = new ArrayList<>();

        while (!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            UserAmount lender = maxHeap.poll();
            UserAmount borrower = minHeap.poll();

            Transaction t = new Transaction();

            if (lender != null && borrower != null) {
                t.setFrom(borrower.getUser());
                t.setTo(lender.getUser());

                if (Math.abs(borrower.getAmount()) < lender.getAmount()) {
                    t.setAmount(Math.abs(borrower.getAmount()));

                    int i = lender.getAmount() - Math.abs(borrower.getAmount());
                    if (!(i == 0)) {
                        maxHeap.add(new UserAmount(lender.getUser(), i));
                    }
                } else {
                    t.setAmount(lender.getAmount());

                    int i = borrower.getAmount() + lender.getAmount();
                    if (!(i == 0)) {
                        minHeap.add(new UserAmount(borrower.getUser(), i));
                    }
                }
            }
            settleUpTransactions.add(t);
        }

        return settleUpTransactions;
    }
}

@Getter
@Setter
class UserAmount {
    private User user;
    private Integer amount;

    public UserAmount(User user, Integer amount) {
        this.user = user;
        this.amount = amount;
    }
}
