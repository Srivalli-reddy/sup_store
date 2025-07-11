package subject.store.supplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import subject.store.supplement.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
