package jnetgraph;

import jnetgraph.probe.SpeedtestCLIImpl;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JNetGraph {

    public static void main(String[] args) {

        // Run the Spring application.
        SpringApplication.run(JNetGraph.class, args);

    }

}
