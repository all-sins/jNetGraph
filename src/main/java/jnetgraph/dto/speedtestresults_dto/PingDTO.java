package jnetgraph.dto.speedtestresults_dto;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PingDTO {

    @JsonProperty("jitter")
    private float jitter;
    @JsonProperty("latency")
    private float latency;

    public float getJitter() {
        return jitter;
    }

    public void setJitter(float jitter) {
        this.jitter = jitter;
    }

    public float getLatency() {
        return latency;
    }

    public void setLatency(float latency) {
        this.latency = latency;
    }

    @Override
    public String toString() {
        return "PingDTO{" +
                "jitter=" + jitter +
                ", latency=" + latency +
                '}';
    }
}
