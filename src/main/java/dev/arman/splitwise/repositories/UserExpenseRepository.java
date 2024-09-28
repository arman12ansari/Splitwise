package dev.arman.splitwise.repositories;

import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {

    List<UserExpense> findAllByUser(User user);

    List<UserExpense> findAllByExpenseIn(List<Expense> expenses);
}
