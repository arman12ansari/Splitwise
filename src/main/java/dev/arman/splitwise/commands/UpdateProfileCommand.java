package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.UserController;
import dev.arman.splitwise.dtos.UpdateProfileRequestDto;
import dev.arman.splitwise.dtos.UpdateProfileResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class UpdateProfileCommand implements Command {
    //u1 UpdateProfile robinchwan
    private final UserController userController;

    public UpdateProfileCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 3 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.UPDATE_PROFILE);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        String phoneNumber = inputWords.get(0);
        String password = inputWords.get(2);

        UpdateProfileRequestDto updateProfileRequestDto = new UpdateProfileRequestDto();
        updateProfileRequestDto.setPhoneNumber(phoneNumber);
        updateProfileRequestDto.setPassword(password);

        UpdateProfileResponseDto updateProfileResponseDto = userController.updateProfile(updateProfileRequestDto);

        System.out.println(updateProfileResponseDto.getStatus());
        System.out.println(updateProfileResponseDto.getMessage());
    }
}
