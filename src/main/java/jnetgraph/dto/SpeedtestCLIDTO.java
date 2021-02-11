package jnetgraph.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jnetgraph.model.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

public class SpeedtestCLIDTO {

    @JsonProperty("stcliId")
    private Long stcliId;

    @JsonProperty("execTimestamp")
    private Date execTimestamp;

    @JsonProperty("jitterMS")
    private float jitterMS;

    @JsonProperty("latencyMS")
    private float latencyMS;

    @JsonProperty("downloadSpeedMbps")
    private float downloadSpeedMbps;

    @JsonProperty("uploadSpeedMbps")
    private float uploadSpeedMbps;

    @JsonProperty("packetLossPercentage")
    private float packetLossPercentage;


    public SpeedtestCLIDTO() {
    }

    public SpeedtestCLIDTO(Long stcliId, Date execTimestamp, float jitterMS, float latencyMS, float downloadSpeedMbps, float uploadSpeedMbps, float packetLossPercentage) {
        this.stcliId = stcliId;
        this.execTimestamp = execTimestamp;
        this.jitterMS = jitterMS;
        this.latencyMS = latencyMS;
        this.downloadSpeedMbps = downloadSpeedMbps;
        this.uploadSpeedMbps = uploadSpeedMbps;
        this.packetLossPercentage = packetLossPercentage;
    }

    public Long getStcliId() {
        return stcliId;
    }

    public void setStcliId(Long stcliId) {
        this.stcliId = stcliId;
    }

    public Date getExecTimestamp() {
        return execTimestamp;
    }

    public void setExecTimestamp(Date execTimestamp) {
        this.execTimestamp = execTimestamp;
    }

    public float getJitterMS() {
        return jitterMS;
    }

    public void setJitterMS(float jitterMS) {
        this.jitterMS = jitterMS;
    }

    public float getLatencyMS() {
        return latencyMS;
    }

    public void setLatencyMS(float latencyMS) {
        this.latencyMS = latencyMS;
    }

    public float getDownloadSpeedMbps() {
        return downloadSpeedMbps;
    }

    public void setDownloadSpeedMbps(float downloadSpeedMbps) {
        this.downloadSpeedMbps = downloadSpeedMbps;
    }

    public float getUploadSpeedMbps() {
        return uploadSpeedMbps;
    }

    public void setUploadSpeedMbps(float uploadSpeedMbps) {
        this.uploadSpeedMbps = uploadSpeedMbps;
    }

    public float getPacketLossPercentage() {
        return packetLossPercentage;
    }

    public void setPacketLossPercentage(float packetLossPercentage) {
        this.packetLossPercentage = packetLossPercentage;
    }


}
