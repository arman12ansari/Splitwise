package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.AddGroupRequestDto;
import dev.arman.splitwise.dtos.AddGroupResponseDto;
import dev.arman.splitwise.exceptions.GroupAlreadyExistsException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Group;
import dev.arman.splitwise.services.GroupService;
import org.springframework.stereotype.Controller;

/**
 * @author mdarmanansari
 */
@Controller
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public AddGroupResponseDto addGroup(AddGroupRequestDto request) {
        Group group;
        AddGroupResponseDto response = new AddGroupResponseDto();

        try {
            group = groupService.createGroup(request.getUserId(), request.getGroupName(), request.getGroupDescription());
            response.setGroupId(group.getId());
            response.setStatus("SUCCESS");
            response.setMessage("Group created successfully");
        } catch (GroupAlreadyExistsException | UserNotFoundException groupAlreadyExistsException) {
            response.setStatus("FAILURE");
            response.setMessage(groupAlreadyExistsException.getMessage());
        }

        return response;
    }
}
