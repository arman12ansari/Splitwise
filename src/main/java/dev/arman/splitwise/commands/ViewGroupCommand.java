package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.UserController;
import dev.arman.splitwise.dtos.ViewGroupRequestDto;
import dev.arman.splitwise.dtos.ViewGroupResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class ViewGroupCommand implements Command {
    //u1 Groups
    private final UserController userController;

    public ViewGroupCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 2 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.VIEW_GROUP);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long userId = Long.parseLong(inputWords.get(0));

        ViewGroupRequestDto request = new ViewGroupRequestDto();
        request.setUserId(userId);

        ViewGroupResponseDto response = userController.viewGroup(request);
        System.out.println("Groups:" + response.getGroupNames());
        System.out.println(response.getStatus());
        System.out.println(response.getMessage());

    }
}
