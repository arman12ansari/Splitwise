package dev.arman.splitwise.services;

import dev.arman.splitwise.exceptions.GroupAlreadyExistsException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Group;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.repositories.GroupRepository;
import dev.arman.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(Long UserId, String groupName, String groupDescription)
            throws GroupAlreadyExistsException, UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(UserId);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found, Please register yourself as user");
        }

        User user = optionalUser.get();

        Optional<Group> optionalGroup = groupRepository.findByName(groupName);
        if (optionalGroup.isPresent()) {
            throw new GroupAlreadyExistsException("Group already exists");
        }

        Group group = new Group();
        group.setName(groupName);
        group.setDescription(groupDescription);
        group.setCreatedBy(user);

        return groupRepository.save(group);
    }

}
