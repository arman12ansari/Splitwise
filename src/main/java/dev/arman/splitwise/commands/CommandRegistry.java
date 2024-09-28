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

    public CommandRegistry(RegisterUserCommand registerUserCommand, UpdateProfileCommand updateProfileCommand) {
        commands = new ArrayList<>();
        commands.add(registerUserCommand);
        commands.add(updateProfileCommand);
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
