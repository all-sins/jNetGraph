package jnetgraph.mapper;
import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.dto.speedtestResultsDTO.PingDTO;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.dto.speedtestResultsDTO.SpeedDataDTO;
import org.springframework.stereotype.Component;

@Component
public class SpeedtestCLIMapper {


    //Maps SpeedtestCLI Object from data from speedtestResultsDTO package
    public SpeedtestCLI dataToObject(SpeedDataDTO speedDataDTO) {
        PingDTO ping = speedDataDTO.getPing();
        float packetLoss = speedDataDTO.getPacketLoss();

        return new SpeedtestCLI(
                ping.getJitter(),
                ping.getLatency(),
                speedDataDTO.getDownloadSpeed(),
                speedDataDTO.getUploadSpeed(),
                packetLoss);

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
