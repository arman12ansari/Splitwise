package dev.arman.splitwise.repositories;

import dev.arman.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mdarmanansari
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    Optional<User> findById(Long id);

    Optional<User> findByPhone(String phone);

    @Override
    User save(User user);
}
