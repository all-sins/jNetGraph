package jnetgraph.service;

import jnetgraph.dto.speedtestResultsDTO.SpeedDataDTO;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.mapper.StringToDate;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import jnetgraph.probe.SpeedtestCLIImpl;
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


    public void createNewEntry(User user) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (check) {
                    LOGGER.info("Running Ookla speedtest and collecting results");
                    SpeedDataDTO speedDataDTO = null;
                    speedDataDTO = speedtestCLIImpl.getData();
                    SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
                    speedtestCLI.setUser(user);
                    LOGGER.debug("Saving entry to database: " + ReflectionToStringBuilder.reflectionToString(speedtestCLI, RecursiveToStringStyle.SIMPLE_STYLE));
                    speedtestCLIRepository.save(speedtestCLI);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new SpeedtestCLIProcessingException("302", "Thread processing error while creating new SpeedtestCLI entries!");
                    }
                }
            }
        });
        thread.start();
    }


    public List<SpeedtestCLI> getDataForPeriod(String startDate, String endDate, String userId) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(stringToDate.convert(endDate));
        c.add(Calendar.DATE,1);
        return speedtestCLIRepository.getDataForPeriod(stringToDate.convert(startDate), c.getTime(), Long.parseLong(userId));
    }


}









