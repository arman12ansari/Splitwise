package dev.arman.splitwise.commands;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class CommandRegistry {
    private List<Command> commands;

    public CommandRegistry(RegisterUserCommand registerUserCommand, UpdateProfileCommand updateProfileCommand,
                           AddGroupCommand addGroupCommand, AddMemberCommand addMemberCommand,
                           ViewGroupCommand viewGroupCommand, AddGroupExpenseCommand addGroupExpenseCommand,
                           AddSinglePayerIndividualExpenseByEqualCommand addSinglePayerIndividualExpenseByEqualCommand,
                           AddMultiPayerIndividualExpenseByPercentCommand addMultiPayerIndividualExpenseByPercentCommand) {
        commands = new ArrayList<>();
        commands.add(registerUserCommand);
        commands.add(updateProfileCommand);
        commands.add(addGroupCommand);
        commands.add(addMemberCommand);
        commands.add(viewGroupCommand);
        commands.add(addGroupExpenseCommand);
        commands.add(addSinglePayerIndividualExpenseByEqualCommand);
        commands.add(addMultiPayerIndividualExpenseByPercentCommand);
    }

    public void execute(String input) {
        for (Command command : commands) {
            if (command.matches(input)) {
                command.execute(input);
                break;
            }
        }
    }
}
