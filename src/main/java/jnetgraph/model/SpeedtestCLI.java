package jnetgraph.model;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "speedtest_cli")
public class SpeedtestCLI {

    @Id
    @Column(name = "stcli_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stcliId;

    @CreationTimestamp
    @Column(name = "exec_timestamp")
    private Date execTimestamp;

    @Column(name = "jitter_ms")
    private float jitterMS;

    @Column(name = "latency_ms")
    private float latencyMS;

    @Column(name = "downloadspeed_mbps")
    private float downloadSpeedMbps;

    @Column(name = "uploadspeed_mbps")
    private float uploadSpeedMbps;

    @Column(name = "packetloss_percentage")
    private float packetLossPercentage;

    @ManyToOne
    @JoinColumn(name = "user_fk")
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
