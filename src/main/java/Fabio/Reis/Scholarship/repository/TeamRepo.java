package Fabio.Reis.Scholarship.repository;

import Fabio.Reis.Scholarship.model.teamEntity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team,Long> {
}
