package jnetgraph.repository;

import jnetgraph.model.SpeedtestCLI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface SpeedtestCLIRepository extends JpaRepository<SpeedtestCLI, Long> {
    //TODO: in >= equals part is not being taken into account due to parsed date
    @Query("select s from SpeedtestCLI s where s.execTimestamp >= :startDate and s.execTimestamp <= :endDate")
    List<SpeedtestCLI> getDataForPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
