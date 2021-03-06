package jnetgraph.dto.speedtestResultsDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadDTO {

    @JsonProperty("bytes")
    private int bytes;
    @JsonProperty("elapsed")
    private int elapsed;

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    @Override
    public String toString() {
        return "DownloadDTO{" +
                "bytes=" + bytes +
                ", elapsed=" + elapsed +
                '}';
    }
}
