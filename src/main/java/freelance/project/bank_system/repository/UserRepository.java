package freelance.project.bank_system.repository;

import freelance.project.bank_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findUserById(UUID id);

    @Query("SELECT a.id FROM Account a WHERE a.user.id = :id")
    Optional<List<UUID>> findAccountByUserId(@Param("id") UUID id);

}
