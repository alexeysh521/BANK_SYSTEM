package freelance.project.bank_system.repository;

import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUser(User user);

    List<Account> findAllByUser(User user);

    @Query("SELECT a.id FROM Account a WHERE a.user.id = :user_id")
    Optional<List<UUID>> findAllByUserId(@Param("user_id") UUID user_id);

}
