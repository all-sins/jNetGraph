package jnetgraph.service;

import jnetgraph.dto.speedtestResultsDTO.SpeedDataDTO;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.mapper.StringToDate;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import jnetgraph.probe.SpeedtestCLIMeasurer;
import jnetgraph.repository.SpeedtestCLIRepository;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;


@Component
public class SpeedtestCLIService {
    private final SpeedtestCLIRepository speedtestCLIRepository;
    private final SpeedtestCLIMeasurer speedtestCLIMeasurer;
    private final SpeedtestCLIMapper speedtestCLIMapper;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIService.class);
    private final StringToDate stringToDate;
    private boolean check = false;

    @Autowired
    public SpeedtestCLIService(SpeedtestCLIRepository speedtestCLIRepository, SpeedtestCLIMeasurer speedtestCLIMeasurer, SpeedtestCLIMapper speedtestCLIMapper, StringToDate stringToDate) {
        this.speedtestCLIRepository = speedtestCLIRepository;
        this.speedtestCLIMeasurer = speedtestCLIMeasurer;
        this.speedtestCLIMapper = speedtestCLIMapper;
        this.stringToDate = stringToDate;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }



    public void createNewEntry(User user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (check) {
                    long start = System.currentTimeMillis();

                    LOGGER.info("Running Ookla speedtest and collecting results");
                    SpeedDataDTO speedDataDTO = null;
                    speedDataDTO = speedtestCLIMeasurer.getData();
                    SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO);
                    speedtestCLI.setUser(user);
                    System.out.println("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
                    LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
                    speedtestCLIRepository.save(speedtestCLI);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new SpeedtestCLIProcessingException("302", "Thread processing error while creating new SpeedtestCLI entries!");
                    }
                    LOGGER.debug(String.valueOf(System.currentTimeMillis() - start));
                }
            }
        });
        thread.start();
    }

    public List<SpeedtestCLI> getAll(){
        LOGGER.info("Retrieving all SpeedTestCLI entries.");
        return speedtestCLIRepository.findAll();
    }

    public List<SpeedtestCLI> getAllForUser(Long id){
        LOGGER.debug("Retrieving all SpeedTestCLI entries for user with ID: "  + id);
        return speedtestCLIRepository.getDataPerUser(id);
    }


    public List<SpeedtestCLI> getDataForPeriod(String startDate, String endDate, String userId) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(stringToDate.convert(endDate));
        calendar.add(Calendar.HOUR,24);
        LOGGER.debug("Retrieving all SpeedTestCLI entries for user with ID: "  + userId + " from " + startDate + " till " + endDate);
        return speedtestCLIRepository.getDataForPeriod(stringToDate.convert(startDate), calendar.getTime(), Long.parseLong(userId));
    }


}









