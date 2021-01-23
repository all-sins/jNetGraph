package jnetgraph.service;

import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import jnetgraph.probe.SpeedtestCLIImpl;
import jnetgraph.probe.speedtestResultsDTO.SpeedDataDTO;
import jnetgraph.repository.SpeedtestCLIRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;


@Component
public class SpeedtestCLIService {
    private final SpeedtestCLIRepository speedtestCLIRepository;
    private final SpeedtestCLIImpl speedtestCLIImpl;
    private final SpeedtestCLIMapper speedtestCLIMapper;
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIService.class);

    @Autowired
    public SpeedtestCLIService(SpeedtestCLIRepository speedtestCLIRepository, SpeedtestCLIImpl speedtestCLIImpl, SpeedtestCLIMapper speedtestCLIMapper) {
        this.speedtestCLIRepository = speedtestCLIRepository;
        this.speedtestCLIImpl = speedtestCLIImpl;
        this.speedtestCLIMapper = speedtestCLIMapper;
    }

    public SpeedtestCLI createNewEntry(User user) throws IOException {
        LOGGER.info("Running Ookla speedtest and collecting results");
        SpeedDataDTO speedDataDTO = speedtestCLIImpl.getData();
        SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);
        speedtestCLI.setUser(user);
        return speedtestCLIRepository.save(speedtestCLI);
    }
}
