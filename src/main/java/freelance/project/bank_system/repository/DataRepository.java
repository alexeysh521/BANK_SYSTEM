package freelance.project.bank_system.repository;

import freelance.project.bank_system.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DataRepository extends JpaRepository<Data, UUID> {

}
