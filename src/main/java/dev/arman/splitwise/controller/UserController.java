package dev.arman.splitwise.controller;

import dev.arman.splitwise.dtos.RegisterUserRequestDto;
import dev.arman.splitwise.dtos.RegisterUserResponseDto;
import dev.arman.splitwise.exceptions.UserAlreadyExistsException;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.services.UserService;
import org.springframework.stereotype.Controller;

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
}
