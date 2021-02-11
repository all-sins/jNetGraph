package jnetgraph.repository;

import jnetgraph.model.TsuImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TsuImplRepository extends JpaRepository<TsuImpl, Long> {
}
