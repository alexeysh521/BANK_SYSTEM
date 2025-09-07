package freelance.project.bank_system.repository;

import freelance.project.bank_system.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Query("SELECT t.id FROM Transaction t WHERE t.fromAccountId.id = :id")
    List<UUID> findAllByAccountId(@Param("id") UUID id);

}
