package jnetgraph.mapper;

import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.probe.SpeedtestCLIImpl;
import jnetgraph.dto.speedtestresults_dto.DownloadDTO;
import jnetgraph.dto.speedtestresults_dto.PingDTO;
import jnetgraph.dto.speedtestresults_dto.SpeedDataDTO;
import jnetgraph.dto.speedtestresults_dto.UploadDTO;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpeedtestCLIMapperTest {

    private SpeedtestCLIImpl speedtestCLIImpl = new SpeedtestCLIImpl();
    private SpeedtestCLIMapper speedtestCLIMapper = new SpeedtestCLIMapper();
    private SpeedDataDTO speedDataDTO = new SpeedDataDTO();
    private PingDTO pingDTO = new PingDTO();
    private  DownloadDTO downloadDTO = new DownloadDTO();
    private UploadDTO uploadDTO = new UploadDTO();


    @Test
    public void dataToObject() {

        speedDataDTO.setDownload(downloadDTO);
        speedDataDTO.setUpload(uploadDTO);
        speedDataDTO.setPing(pingDTO);
        pingDTO.setJitter(0.40799999999999997f);
        pingDTO.setLatency(2.1230000000000002f);
        downloadDTO.setBytes(32926347);
        downloadDTO.setElapsed(7816);
        uploadDTO.setBytes(99942474);
        uploadDTO.setElapsed(15015);
        speedDataDTO.setPacketLoss(0.9f);


        SpeedtestCLI speedtestCLI = speedtestCLIMapper.dataToObject(speedDataDTO, speedtestCLIImpl);

        assertEquals(0.40799999999999997f, speedtestCLI.getJitterMS(),0);
        assertEquals(2.1230000000000002f, speedtestCLI.getLatencyMS(),0);
        assertEquals(0.9f, speedtestCLI.getPacketLossPercentage(),0);
        assertEquals(speedtestCLIImpl.downloadSpeed(speedDataDTO.getDownload().getBytes(), speedDataDTO.getDownload().getElapsed()),
                    speedtestCLI.getDownloadSpeedMbps(),0);
        assertEquals(speedtestCLIImpl.uploadSpeed(speedDataDTO.getUpload().getBytes(), speedDataDTO.getUpload().getElapsed()),
                    speedtestCLI.getUploadSpeedMbps(), 0);

    }

    @Test
    public void toDTO() {
        SpeedtestCLI speedtestCLI = new SpeedtestCLI();
        speedtestCLI.setDownloadSpeedMbps(30.50f);
        speedtestCLI.setUploadSpeedMbps(40.20f);
        speedtestCLI.setPacketLossPercentage(5);
        speedtestCLI.setLatencyMS(1);
        speedtestCLI.setJitterMS(2.8956f);



        SpeedtestCLIDTO speedtestCLIDTO = speedtestCLIMapper.toDTO(speedtestCLI);

        assertEquals(30.50f, speedtestCLIDTO.getDownloadSpeedMbps(), 0);
        assertEquals(40.20f, speedtestCLIDTO.getUploadSpeedMbps(),0);
        assertEquals(5, speedtestCLIDTO.getPacketLossPercentage(),0);
        assertEquals(1, speedtestCLIDTO.getLatencyMS(),0);
        assertEquals(2.8956f, speedtestCLIDTO.getJitterMS(),0);

    }
}
