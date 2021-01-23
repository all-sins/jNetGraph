package jnetgraph.repository;

import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeedtestCLIRepository extends JpaRepository<SpeedtestCLI, Long> {
}
