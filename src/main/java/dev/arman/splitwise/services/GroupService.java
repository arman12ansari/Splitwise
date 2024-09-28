package dev.arman.splitwise.services;

import dev.arman.splitwise.exceptions.GroupAlreadyExistsException;
import dev.arman.splitwise.exceptions.GroupNotFoundException;
import dev.arman.splitwise.exceptions.MemberAlreadyExistsException;
import dev.arman.splitwise.exceptions.UserNotFoundException;
import dev.arman.splitwise.models.Group;
import dev.arman.splitwise.models.User;
import dev.arman.splitwise.repositories.GroupRepository;
import dev.arman.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        group.setMembers(List.of(user));

        return groupRepository.save(group);
    }

    public Group addMember(long groupCreatorId, long groupId, long memberId)
            throws UserNotFoundException, GroupNotFoundException, MemberAlreadyExistsException {
        Optional<User> optionalGroupCreator = userRepository.findById(groupCreatorId);

        if (optionalGroupCreator.isEmpty()) {
            throw new UserNotFoundException("User not found, Please register yourself as user");
        }

        Optional<User> optionalMember = userRepository.findById(memberId);

        if (optionalMember.isEmpty()) {
            throw new UserNotFoundException("Member not found, Please ask member to register first as user");
        }

        Optional<Group> optionalGroup = groupRepository.findById(groupId);

        if (optionalGroup.isEmpty()) {
            throw new GroupNotFoundException("Group not found, Please create group first");
        }

        if (optionalGroup.get().getMembers() != null) {
            for (User member : optionalGroup.get().getMembers()) {
                if (member.getId() == memberId) {
                    throw new MemberAlreadyExistsException("Member already exists in group");
                }
            }
        }

        Group group = optionalGroup.get();

        if (group.getCreatedBy().getId() != groupCreatorId) {
            throw new GroupNotFoundException("You are not the creator of this group, Please ask admin to add Member");
        }


        if (group.getMembers() == null) {
            group.setMembers(List.of(optionalMember.get()));
        } else {
            group.getMembers().add(optionalMember.get());
        }

        return groupRepository.save(group);
    }

}
