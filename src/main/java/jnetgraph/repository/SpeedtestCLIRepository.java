package jnetgraph.repository;

import jnetgraph.model.SpeedtestCLI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SpeedtestCLIRepository extends JpaRepository<SpeedtestCLI, Long> {

    @Query("select s from SpeedtestCLI s where s.execTimestamp >= :startDate and s.execTimestamp < :endDate and s.user.id = :userId")
    List<SpeedtestCLI> getDataForPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Long userId);

    @Query("select s from SpeedtestCLI s where s.user.id = :id")
    List<SpeedtestCLI> getDataPerUser(@Param("id") Long id);

}
