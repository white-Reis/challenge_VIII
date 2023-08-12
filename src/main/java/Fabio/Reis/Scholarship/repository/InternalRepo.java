package Fabio.Reis.Scholarship.repository;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalRepo extends JpaRepository<Internal, Long> {
    Optional<Internal> findByEmail(String email);
}
