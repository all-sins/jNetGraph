package jnetgraph.probe;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.dto.speedtestresults_dto.SpeedDataDTO;
import jnetgraph.exception.SpeedtestCLIProcessingException;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class SpeedtestCLIImpl {

    @Value("${speedtest.path}")
    private String speedtestPath;

    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIImpl.class);

    public void setSpeedtestPath(String speedtestPath) {
        this.speedtestPath = speedtestPath;
    }

    //The actual implementation of SpeedtestCLI. When method called, it runs the .exe file, gets data as json
    // and returns SpeedDataDTO object that can further be used to create final object - SpeedtestCLI
    public SpeedDataDTO getData() {
        StringBuffer output = new StringBuffer();
        //TODO:nav smuki viena burta nosaukumus lietot
        Process p;
        try {
            p = Runtime.getRuntime().exec(speedtestPath + " -f json");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
            //TODO:labaak konkreetu exception lietot - IO piemeeram
        } catch (Exception e) {

            throw new SpeedtestCLIProcessingException("301", "getData() failed on running/reading data from Ookla speedtest.exe", e);

        }
        LOGGER.debug("Data received from speedtest.exe: " + output.toString());
        try {
            SpeedDataDTO speedDataDTO = new ObjectMapper().readValue(output.toString(), SpeedDataDTO.class);
            LOGGER.debug("DTO object created from JSON string:" + "\n" + ReflectionToStringBuilder.reflectionToString(speedDataDTO, RecursiveToStringStyle.MULTI_LINE_STYLE));
            return speedDataDTO;
        } catch (IOException e) {
            //printscacktrace vietaa. Nevajag loggeri veel ja ir throw jau
            LOGGER.error(e.getMessage(),e);
            throw new SpeedtestCLIProcessingException("300", "getData() failed on creating SpeedDataDTO object from json file");
        }


    }

    public float downloadSpeed(int bytes, int elapsed) {
        int mb = (bytes / 1024) / 1024;
        float s = elapsed / 1000;
        return (mb / s) * 8;
    }

    public float uploadSpeed(int bytes, int elapsed) {
        int mb = (bytes / 1024) / 1024;
        float s = elapsed / 1000;
        return (mb / s) * 8;
    }

}


