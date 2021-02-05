package jnetgraph.mapper;
import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.dto.UserDTO;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.model.User;
import jnetgraph.probe.SpeedtestCLIImpl;
import jnetgraph.probe.speedtestResultsDTO.SpeedDataDTO;
import org.springframework.stereotype.Component;

@Component
public class SpeedtestCLIMapper {

    public SpeedtestCLI dataToObject(SpeedDataDTO speedDataDTO, SpeedtestCLIImpl speedtestCLIImpl) {
        return new SpeedtestCLI(
                speedDataDTO.getPing().getJitter(),
                speedDataDTO.getPing().getLatency(),
                speedtestCLIImpl.downloadSpeed(speedDataDTO.getDownload().getBytes(), speedDataDTO.getDownload().getElapsed()),
                speedtestCLIImpl.uploadSpeed(speedDataDTO.getUpload().getBytes(), speedDataDTO.getUpload().getElapsed()),
                speedDataDTO.getPacketLoss());

    }

    public SpeedtestCLIDTO toDTO(SpeedtestCLI speedtestCLI) {
        return new SpeedtestCLIDTO(
              speedtestCLI.getStcliId(),
                speedtestCLI.getExecTimestamp(),
                speedtestCLI.getJitterMS(),
                speedtestCLI.getLatencyMS(),
                speedtestCLI.getDownloadSpeedMbps(),
                speedtestCLI.getUploadSpeedMbps(),
                speedtestCLI.getPacketLossPercentage()

        );
    }
}