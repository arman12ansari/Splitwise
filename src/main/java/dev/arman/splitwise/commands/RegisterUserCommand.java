package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.UserController;
import dev.arman.splitwise.dtos.RegisterUserRequestDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class RegisterUserCommand implements Command {
    // Register vinsmokesanji 003 namisswwaann
    private final UserController userController;

    public RegisterUserCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 4 && inputWords.get(0).equalsIgnoreCase(CommandKeyword.REGISTER_USER);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();

        String userName = inputWords.get(1);
        String phoneNumber = inputWords.get(2);
        String password = inputWords.get(3);

        RegisterUserRequestDto registerUserRequestDto = new RegisterUserRequestDto();
        registerUserRequestDto.setUserName(userName);
        registerUserRequestDto.setPhoneNumber(phoneNumber);
        registerUserRequestDto.setPassword(password);

        userController.registerUser(registerUserRequestDto);
    }
}
