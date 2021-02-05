package jnetgraph.service;

import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.mapper.StringToDate;
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
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Component
public class SpeedtestCLIService {
    private final SpeedtestCLIRepository speedtestCLIRepository;
    private final SpeedtestCLIImpl speedtestCLIImpl;
    private final SpeedtestCLIMapper speedtestCLIMapper;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIService.class);
    private final StringToDate stringToDate;
    private boolean check = false;

    @Autowired
    public SpeedtestCLIService(SpeedtestCLIRepository speedtestCLIRepository, SpeedtestCLIImpl speedtestCLIImpl, SpeedtestCLIMapper speedtestCLIMapper, StringToDate stringToDate) {
        this.speedtestCLIRepository = speedtestCLIRepository;
        this.speedtestCLIImpl = speedtestCLIImpl;
        this.speedtestCLIMapper = speedtestCLIMapper;
        this.stringToDate = stringToDate;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }


    //    public void createNewEntry(User user) throws IOException, InterruptedException {
//        while (check.equals("get data")) {
//            LOGGER.info("Running Ookla speedtest and collecting results");
//            SpeedDataDTO speedDataDTO = speedtestCLIImpl.getData();
//            SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
//            speedtestCLI.setUser(user);
//            LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
//            speedtestCLIRepository.save(speedtestCLI);
//            LOGGER.info("Waiting 1 hour to get next measurement");
//            Thread.sleep(1000);
//
//        }
//    }


    public void createNewEntry(User user) throws IOException, InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (check) {
                    LOGGER.info("Running Ookla speedtest and collecting results");
                    SpeedDataDTO speedDataDTO = null;
                    try {
                        speedDataDTO = speedtestCLIImpl.getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
                    speedtestCLI.setUser(user);
                    LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
                    speedtestCLIRepository.save(speedtestCLI);
                    LOGGER.info("Waiting 1 hour to get next measurement");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.run();
    }


    public List<SpeedtestCLI> getDataForPeriod(String startDate, String endDate) throws ParseException {

        return speedtestCLIRepository.getDataForPeriod(stringToDate.convert(startDate), stringToDate.convert(endDate));

    }




}









