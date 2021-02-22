package jnetgraph.probe;

import jnetgraph.dto.speedtestResultsDTO.SpeedDataDTO;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpeedtestCLIMeasurerTest {
    private SpeedtestCLIMeasurer speedtestCLIMeasurer = new SpeedtestCLIMeasurer();

//    @Test
//    public void downloadSpeed() {
//        int bytes = 15000000;
//        int elapsed = 300000;
//
//        float testDownloadSpeed = speedtestCLIImpl.downloadSpeed(bytes, elapsed);
//        //int mb = (15000000/1024)/1024 = 14,30511474609375
//        //float s = 300000/1000 = 300
//        //result = (mb/s)*8 = 0,3814697265625
//        assertEquals(0.3814697265625f, testDownloadSpeed, 0.1);
//
//
//    }
//
//    @Test
//    public void uploadSpeed() {
//        int bytes = 15000000;
//        int elapsed = 300000;
//
//        float testUploadSpeed = speedtestCLIImpl.uploadSpeed(bytes, elapsed);
//        //int mb = (15000000/1024)/1024 = 14,30511474609375
//        //float s = 300000/1000 = 300
//        //result = (mb/s)*8 = 0,3814697265625
//        assertEquals(0.3814697265625f, testUploadSpeed, 0.1);
//    }

    @Test
    public void getDataReturnsSpeedDataDTOObject() {
        speedtestCLIMeasurer.setSpeedtestPath("C://speedtest.exe");
        SpeedDataDTO speedDataDTO = speedtestCLIMeasurer.getData();
        assertNotNull(speedDataDTO);
    }

    @Test(expected = RuntimeException.class)
    public void getDataThrowsErrorWhenFileNotAvailable() {
        speedtestCLIMeasurer.setSpeedtestPath("C://test//speedtest.exe");
        try {
            SpeedDataDTO speedDataDTO = speedtestCLIMeasurer.getData();
        } catch (SpeedtestCLIProcessingException e) {
            String errorMessage = "getData() failed on running/reading data from Ookla speedtest.exe";
            String errorCode = "301";
            assertEquals(errorMessage, e.getMessage());
            assertEquals(errorCode, e.getCode());
            throw e;

        }

    }
}
