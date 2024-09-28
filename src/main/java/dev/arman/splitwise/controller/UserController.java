package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.*;
import dev.arman.splitwise.exceptions.GroupNotFoundException;
import dev.arman.splitwise.exceptions.UserAlreadyExistsException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Group;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.services.UserService;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdarmanansari
 */
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto request) {
        User user;
        RegisterUserResponseDto response = new RegisterUserResponseDto();

        try {
            user = userService.registerUser(request.getUserName(), request.getPhoneNumber(), request.getPassword());
            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
            response.setMessage("User registered successfully");

        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            response.setStatus("FAILURE");
            response.setMessage(userAlreadyExistsException.getMessage());

        }
        return response;
    }

    public UpdateProfileResponseDto updateProfile(UpdateProfileRequestDto request) {
        User user;
        UpdateProfileResponseDto response = new UpdateProfileResponseDto();

        try {
            user = userService.updateProfile(request.getPhoneNumber(), request.getPassword());
            response.setUserId(user.getId());
            response.setStatus("SUCCESS");
            response.setMessage("User profile updated successfully");

        } catch (UserNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());

        }
        return response;
    }

    public ViewGroupResponseDto viewGroup(ViewGroupRequestDto request) {
        List<String> groupNames = new ArrayList<>();
        ViewGroupResponseDto response = new ViewGroupResponseDto();

        try {
            List<Group> groups = userService.viewGroups(request.getUserId());

            for (Group group : groups) {
                groupNames.add(group.getName());
            }
            response.setGroupNames(groupNames);
            response.setStatus("SUCCESS");
            response.setMessage("User group viewed successfully");

        } catch (UserNotFoundException | GroupNotFoundException userNotFoundException) {
            response.setStatus("FAILURE");
            response.setMessage(userNotFoundException.getMessage());

        }
        return response;
    }
}
