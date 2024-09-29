package dev.arman.splitwise.services;

import dev.arman.splitwise.exceptions.GroupNotFoundException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Expense;
import dev.arman.splitwise.models.ExpenseType;
import dev.arman.splitwise.models.Group;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.repositories.ExpenseRepository;
import dev.arman.splitwise.repositories.GroupRepository;
import dev.arman.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserExpenseService userExpenseService;

    public ExpenseService(ExpenseRepository expenseRepository, GroupRepository groupRepository, UserRepository userRepository, UserExpenseService userExpenseService) {
        this.expenseRepository = expenseRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userExpenseService = userExpenseService;
    }

    public Expense addGroupExpense(Long groupId, Long paidUserId, String description, int amount)
            throws GroupNotFoundException, UserNotFoundException {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);

        if (optionalGroup.isEmpty()) {
            throw new GroupNotFoundException("Group not found");
        }

        Optional<User> optionalUser = userRepository.findById(paidUserId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        Expense createdExpense = insertGroupExpense(amount, description,
                optionalUser.get(), optionalGroup.get());

        userExpenseService.paidUserExpense(optionalUser.get(), createdExpense, amount);

        int groupSize = optionalGroup.get().getMembers().size();
        int shareAmount = amount / groupSize;
        List<User> members = optionalGroup.get().getMembers();

        for (User member : members) {
            userExpenseService.hadToPayUserExpense(member, createdExpense, shareAmount);
        }


        return createdExpense;
    }

    public Expense addSinglePayerIndividualExpenseByEqual(Long paidUserId, List<Long> owedUserId,
                                                          int amount, String description) throws UserNotFoundException {

        Optional<User> optionalPaidUser = userRepository.findById(paidUserId);
        if (optionalPaidUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        List<User> owedUsers = new ArrayList<>();
        for (Long userId : owedUserId) {
            Optional<User> optionalOwedUser = userRepository.findById(userId);
            if (optionalOwedUser.isEmpty()) {
                throw new UserNotFoundException("User not found");
            }
            owedUsers.add(optionalOwedUser.get());
        }

        Expense createdExpense = insertIndividualExpense(amount, description, optionalPaidUser.get());

        userExpenseService.paidUserExpense(optionalPaidUser.get(), createdExpense, amount);

        int shareAmount = amount / (owedUsers.size() + 1);
        for (User owedUser : owedUsers) {
            userExpenseService.hadToPayUserExpense(owedUser, createdExpense, shareAmount);
        }

        userExpenseService.hadToPayUserExpense(optionalPaidUser.get(), createdExpense, shareAmount);

        return createdExpense;
    }

    public Expense addMultiPayerIndividualExpenseByPercent(Long createdById, List<Long> paidUserId,
                                                           List<Integer> amountPaid, List<Integer> percent,
                                                           String description) throws UserNotFoundException {

        List<User> paidUsers = new ArrayList<>();
        for (Long userId : paidUserId) {
            Optional<User> optionalPaidUser = userRepository.findById(userId);
            if (optionalPaidUser.isEmpty()) {
                throw new UserNotFoundException("User not found");
            }
            paidUsers.add(optionalPaidUser.get());
        }

        Optional<User> optionalCreatedUser = userRepository.findById(createdById);
        if (optionalCreatedUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        int amount = 0;
        for (Integer amt : amountPaid) {
            amount += amt;
        }

        Expense createdExpense = insertIndividualExpense(amount, description, optionalCreatedUser.get());

        int paidCounter = 0;
        for (User paidUser : paidUsers) {
            userExpenseService.paidUserExpense(paidUser, createdExpense, amountPaid.get(paidCounter));
            paidCounter++;
        }

        int percentCounter = 0;
        for (User paidUser : paidUsers) {
            userExpenseService.hadToPayUserExpense(paidUser, createdExpense, (amount * percent.get(percentCounter)) / 100);
            percentCounter++;
        }

        return createdExpense;
    }

    public Expense insertGroupExpense(int amount, String description, User createdBy, Group group) {
        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setCreatedBy(createdBy);
        expense.setGroups(group);
        expense.setExpenseType(ExpenseType.EXPENSE);
        return expenseRepository.save(expense);
    }

    public Expense insertIndividualExpense(int amount, String description, User createdBy) {
        Expense expense = new Expense();
        expense.setAmount(amount);
        expense.setDescription(description);
        expense.setCreatedBy(createdBy);
        expense.setExpenseType(ExpenseType.EXPENSE);
        return expenseRepository.save(expense);
    }
}
