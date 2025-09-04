package freelance.project.bank_system.repository;

import freelance.project.bank_system.model.Account;
import freelance.project.bank_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findAllByUser(User user);

}
