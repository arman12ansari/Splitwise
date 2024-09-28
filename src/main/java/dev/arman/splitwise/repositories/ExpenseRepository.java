package dev.arman.splitwise.repositories;

import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author mdarmanansari
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByGroups(Group group);
}
