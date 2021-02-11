package jnetgraph.dto.speedtestResultsDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
//Ignores properties in json file that are not defined in particular object
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeedDataDTO {

    //Due to the fact that json returned has data nested as child attributes, each "child" is
    // treated as separate DTO object (ping, download and upload)
    //{
    //	"type": "result",
    //	"timestamp": "2021-02-07T17:02:30Z",
    //	"ping": {
    //		"jitter": 0.454,
    //		"latency": 1.82
    //	}


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
