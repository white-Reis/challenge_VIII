package Fabio.Reis.Scholarship.repository;

import Fabio.Reis.Scholarship.model.internalEntity.Internal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternalRepo extends JpaRepository<Internal, Long> {
}
