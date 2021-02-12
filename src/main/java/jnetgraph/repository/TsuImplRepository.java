package jnetgraph.repository;

import jnetgraph.model.TsuImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TsuImplRepository extends JpaRepository<TsuImpl, Long> {

    @Query("select s from TsuImpl s where s.exec_timestamp >= :from and s.exec_timestamp <= :to and s.user.id = :userId")
    List<TsuImpl> getDataForPeriod(@Param("from") Date startDate, @Param("to") Date endDate, @Param("userId") Long userId);

    @Query("select s from TsuImpl s where s.user.id = :userId")
    List<TsuImpl> getDataWithUserID(@Param("userId") Long userId);

}
