package jnetgraph.service;

import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import jnetgraph.probe.SpeedtestCLIImpl;
import jnetgraph.probe.speedtestResultsDTO.SpeedDataDTO;
import jnetgraph.repository.SpeedtestCLIRepository;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;



@Component
public class SpeedtestCLIService{
    private final SpeedtestCLIRepository speedtestCLIRepository;
    private final SpeedtestCLIImpl speedtestCLIImpl;
    private final SpeedtestCLIMapper speedtestCLIMapper;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIService.class);
    private String check;


    @Autowired
    public SpeedtestCLIService(SpeedtestCLIRepository speedtestCLIRepository, SpeedtestCLIImpl speedtestCLIImpl, SpeedtestCLIMapper speedtestCLIMapper) {
        this.speedtestCLIRepository = speedtestCLIRepository;
        this.speedtestCLIImpl = speedtestCLIImpl;
        this.speedtestCLIMapper = speedtestCLIMapper;
    }



    public void setCheck(String check) {
        this.check = check;
    }

    //TODO: Need to repeat this method until user exits it
//    public SpeedtestCLI createNewEntry(User user) throws IOException {
//
//    LOGGER.info("Running Ookla speedtest and collecting results");
//    SpeedDataDTO speedDataDTO = speedtestCLIImpl.getData();
//    SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
//    speedtestCLI.setUser(user);
//    LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
//    return speedtestCLIRepository.save(speedtestCLI);
//
//
//    }

    public void createNewEntry(User user) throws IOException, InterruptedException {
        while(check.equals("get data")) {
            LOGGER.info("Running Ookla speedtest and collecting results");
            SpeedDataDTO speedDataDTO = speedtestCLIImpl.getData();
            SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
            speedtestCLI.setUser(user);
            LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
            speedtestCLIRepository.save(speedtestCLI);
            LOGGER.info("Waiting 1 hour to get next measurement");
            Thread.sleep(10);

        }

    }


    }







