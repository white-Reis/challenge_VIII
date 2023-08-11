package Fabio.Reis.Scholarship.repository;

import Fabio.Reis.Scholarship.model.squadEntity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SquadRepo extends JpaRepository<Squad, Long> {
}
