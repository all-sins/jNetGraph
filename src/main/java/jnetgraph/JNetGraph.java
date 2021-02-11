package jnetgraph;

import jnetgraph.probe.SpeedtestCLIImpl;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JNetGraph {

    public static void main(String[] args) {
       org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SpeedtestCLIImpl.class);

        // Run the Spring application.
        SpringApplication.run(JNetGraph.class, args);

        // Placeholder code.
        LOGGER.info("Our super cool application!");

    }

}
