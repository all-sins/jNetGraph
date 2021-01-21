package jnetgraph.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "speedtest_cli")
public class SpeedtestCLI {

    @Id
    @Column(name = "stcli_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stcli_id;

    @ManyToOne
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "exec_timestamp")
    private Date exec_timestamp;

    @Column(name = "response_time")
    private Float response_time;

    @Column(name = "download_speed")
    private Float download_speed;

    @Column(name = "upload_speed")
    private Float upload_speed;

    // Empty default constructor left here in case
    // there will be another parameterized constructor.
    // Because then you need a default one.
    public SpeedtestCLI() {
    }

    public SpeedtestCLI(Date exec_timestamp, Float response_time, Float download_speed, Float upload_speed) {
        this.exec_timestamp = exec_timestamp;
        this.response_time = response_time;
        this.download_speed = download_speed;
        this.upload_speed = upload_speed;
    }

    public Long getStcli_id() {
        return stcli_id;
    }

    public void setStcli_id(Long stcli_id) {
        this.stcli_id = stcli_id;
    }

    public Date getExec_timestamp() {
        return exec_timestamp;
    }

    public void setExec_timestamp(Date exec_timestamp) {
        this.exec_timestamp = exec_timestamp;
    }

    public Float getResponse_time() {
        return response_time;
    }

    public void setResponse_time(Float response_time) {
        this.response_time = response_time;
    }

    public Float getDownload_speed() {
        return download_speed;
    }

    public void setDownload_speed(Float download_speed) {
        this.download_speed = download_speed;
    }

    public Float getUpload_speed() {
        return upload_speed;
    }

    public void setUpload_speed(Float upload_speed) {
        this.upload_speed = upload_speed;
    }
}
