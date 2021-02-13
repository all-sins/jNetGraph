package jnetgraph.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "speedtestcli")
public class SpeedtestCLI {

    @Id
    @Column(name = "stcliid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stcliId;

    @CreationTimestamp
    @Column(name = "exectimestamp")
    private Date execTimestamp;

    @Column(name = "jitterms")
    private float jitterMS;

    @Column(name = "latencyms")
    private float latencyMS;

    @Column(name = "downloadspeedmbps")
    private float downloadSpeedMbps;

    @Column(name = "uploadspeedmbps")
    private float uploadSpeedMbps;

    @Column(name = "packetlosspercentage")
    private float packetLossPercentage;

    @ManyToOne
    @JoinColumn(name = "userfk")
    private User user;

    // Empty default constructor left here in case
    // there will be another parameterized constructor.
    // Because then you need a default one.
    public SpeedtestCLI() {
    }

    public SpeedtestCLI(float jitterMS, float latencyMS, float downloadSpeedMbps, float uploadSpeedMbps, float packetLossPercentage) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
