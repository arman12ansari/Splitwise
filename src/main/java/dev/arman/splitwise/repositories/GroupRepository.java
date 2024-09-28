package dev.arman.splitwise.repositories;

import dev.arman.splitwise.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Override
    Optional<Group> findById(Long id);

    Optional<Group> findByName(String name);

    @Override
    Group save(Group group);
}
