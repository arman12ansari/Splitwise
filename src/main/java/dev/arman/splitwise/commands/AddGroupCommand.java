package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.GroupController;
import dev.arman.splitwise.dtos.AddGroupRequestDto;
import dev.arman.splitwise.dtos.AddGroupResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class AddGroupCommand implements Command {
    //u1 AddGroup Roommates
    private final GroupController groupController;

    public AddGroupCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 3 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.ADD_GROUP);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long userId = Long.parseLong(inputWords.get(0));
        String groupName = inputWords.get(2);
        String groupDescription = inputWords.get(2);

        AddGroupRequestDto addGroupRequestDto = new AddGroupRequestDto();
        addGroupRequestDto.setUserId(userId);
        addGroupRequestDto.setGroupName(groupName);
        addGroupRequestDto.setGroupDescription(groupDescription);

        AddGroupResponseDto addGroupResponseDto = groupController.addGroup(addGroupRequestDto);

        System.out.println(addGroupResponseDto.getStatus());
        System.out.println(addGroupResponseDto.getMessage());
    }
}
