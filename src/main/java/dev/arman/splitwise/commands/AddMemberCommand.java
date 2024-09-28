package dev.arman.splitwise.commands;

import dev.arman.splitwise.controller.GroupController;
import dev.arman.splitwise.dtos.AddMemberRequestDto;
import dev.arman.splitwise.dtos.AddMemberResponseDto;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Component
public class AddMemberCommand implements Command {
    //u1 AddMember g1 u2
    private final GroupController groupController;

    public AddMemberCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        return inputWords.size() == 4 && inputWords.get(1).equalsIgnoreCase(CommandKeyword.ADD_MEMBER);
    }

    @Override
    public void execute(String input) {
        List<String> inputWords = Arrays.stream(input.split(" ")).toList();
        Long groupCreatorId = Long.parseLong(inputWords.get(0));
        Long groupId = Long.parseLong(inputWords.get(2));
        Long memberId = Long.parseLong(inputWords.get(3));

        AddMemberRequestDto addMemberRequestDto = new AddMemberRequestDto();
        addMemberRequestDto.setGroupCreatorId(groupCreatorId);
        addMemberRequestDto.setGroupId(groupId);
        addMemberRequestDto.setMemberId(memberId);

        AddMemberResponseDto addMemberResponseDto = groupController.addMember(addMemberRequestDto);
        System.out.println(addMemberResponseDto.getStatus());
        System.out.println(addMemberResponseDto.getMessage());
    }
}
