package jnetgraph.probe.speedtestResultsDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeedDataDTO {

    private PingDTO ping;
    private DownloadDTO download;
    private UploadDTO upload;
    @JsonProperty("packetLoss")
    private float packetLoss;


    public PingDTO getPing() {
        return ping;
    }

    public void setPing(PingDTO ping) {
        this.ping = ping;
    }

    public DownloadDTO getDownload() {
        return download;
    }

    public void setDownload(DownloadDTO download) {
        this.download = download;
    }

    public UploadDTO getUpload() {
        return upload;
    }

    public void setUpload(UploadDTO upload) {
        this.upload = upload;
    }

    public float getPacketLoss() {
        return packetLoss;
    }

    public void setPacketLoss(float packetLoss) {
        this.packetLoss = packetLoss;
    }
}
