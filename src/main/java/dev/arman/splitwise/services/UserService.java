package dev.arman.splitwise.services;

import dev.arman.splitwise.exceptions.UserAlreadyExistsException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.models.UserStatus;
import dev.arman.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String userName, String phoneNumber,
                             String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByPhone(phoneNumber);

        if (userOptional.isPresent()) {
            if (userOptional.get().getUserStatus().equals(UserStatus.ACTIVE)) {
                throw new UserAlreadyExistsException("User already exists");
            } else {
                User user = userOptional.get();
                user.setUserStatus(UserStatus.ACTIVE);
                user.setPassword(password);
                user.setName(userName);
                return userRepository.save(user);
            }
        }

        User user = new User();
        user.setPhone(phoneNumber);
        user.setPassword(password);
        user.setUserStatus(UserStatus.ACTIVE);
        user.setName(userName);

        return userRepository.save(user);
    }

    public User updateProfile(String phoneNumber, String password) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findByPhone(phoneNumber);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        User user = userOptional.get();
        user.setPassword(password);

        return userRepository.save(user);
    }
}
