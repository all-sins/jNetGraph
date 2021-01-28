package jnetgraph.probe;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.probe.speedtestResultsDTO.SpeedDataDTO;
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

    public SpeedDataDTO getData() throws IOException {
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(speedtestPath + " -f json");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("Data received from speedtest.exe: " + output.toString());
        SpeedDataDTO speedDataDTO = new ObjectMapper().readValue(output.toString(), SpeedDataDTO.class);
        LOGGER.debug("DTO object created from JSON string:" +"\n" + ReflectionToStringBuilder.reflectionToString(speedDataDTO, RecursiveToStringStyle.MULTI_LINE_STYLE));
        return speedDataDTO;

    }

    public float downloadSpeed(int bytes, int elapsed){
        int mb = (bytes/1024)/1024;
        float s = elapsed/1000;
        return (mb/s)*8;
    }

    public float uploadSpeed(int bytes, int elapsed){
        int mb = (bytes/1024)/1024;
        float s = elapsed/1000;
        return (mb/s)*8;
    }

}


