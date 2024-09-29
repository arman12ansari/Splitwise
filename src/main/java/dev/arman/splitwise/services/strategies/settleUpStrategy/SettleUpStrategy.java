package dev.arman.splitwise.services.strategies.settleUpStrategy;

import dev.arman.splitwise.models.Expense;

import java.util.List;

/**
 * @author mdarmanansari
 */
public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenses);
}
