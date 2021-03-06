package jnetgraph.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class TsuImpl {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tsuimpl_id;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    // What does it do? Why does it work.
    // Why does Spring Boot like to torture my poor soul
    // with these god forsaken annotations???
    @CreationTimestamp
    @Column(name = "exec_timestamp")
    private Date exec_timestamp;

    @Column(name = "response_time")
    private Float response_time;

    @Column(name = "download_speed")
    private Float download_speed;

    public TsuImpl() {
    }

    public TsuImpl(User user, Float response_time, Float download_speed) {
        this.user = user;
        this.response_time = response_time;
        this.download_speed = download_speed;
    }

    // Constructor used for DTO mapper.
    // DTO -> Entity
    public TsuImpl(Date exec_timestamp, Float response_time, Float download_speed) {
        this.exec_timestamp = exec_timestamp;
        this.response_time = response_time;
        this.download_speed = download_speed;
    }

    public TsuImpl(Float response_time, Float download_speed) {
        this.response_time = response_time;
        this.download_speed = download_speed;
    }

    public Long getTsuimpl_id() {
        return tsuimpl_id;
    }

    public void setTsuimpl_id(Long tsuimpl_id) {
        this.tsuimpl_id = tsuimpl_id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
