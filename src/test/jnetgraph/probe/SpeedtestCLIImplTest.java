package jnetgraph.probe;

import jnetgraph.dto.speedtestResultsDTO.SpeedDataDTO;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SpeedtestCLIImplTest {
    private SpeedtestCLIImpl speedtestCLIImpl = new SpeedtestCLIImpl();

    @Test
    public void downloadSpeed() {
        int bytes = 15000000;
        int elapsed = 300000;

        float testDownloadSpeed = speedtestCLIImpl.downloadSpeed(bytes, elapsed);
        //int mb = (15000000/1024)/1024 = 14,30511474609375
        //float s = 300000/1000 = 300
        //result = (mb/s)*8 = 0,3814697265625
        assertEquals(0.3814697265625f, testDownloadSpeed,0.1);


    }

    @Test
    public void uploadSpeed() {
        int bytes = 15000000;
        int elapsed = 300000;

        float testUploadSpeed = speedtestCLIImpl.uploadSpeed(bytes, elapsed);
        //int mb = (15000000/1024)/1024 = 14,30511474609375
        //float s = 300000/1000 = 300
        //result = (mb/s)*8 = 0,3814697265625
        assertEquals(0.3814697265625f, testUploadSpeed,0.1);
    }

    @Test
    public void getDataReturnsSpeedDataDTOObject() throws IOException {
        speedtestCLIImpl.setSpeedtestPath("C://Users//mara//Downloads//ookla-speedtest-1.0.0-win64//speedtest.exe");
        SpeedDataDTO speedDataDTO = speedtestCLIImpl.getData();
        assertNotNull(speedDataDTO);

    }
}
