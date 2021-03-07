package jnetgraph.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class TsuImpl {

    @Id
    @Column(name = "tsuimplid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tsuImplId;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    // "@CreationTimestamp" Marks a property as the creation timestamp of the containing entity. The property value
    // will be set to the current VM date exactly once when saving the owning entity for the first time. There are a
    // variety of supported data types:
    // https://docs.jboss.org/hibernate/orm/5.2/javadocs/org/hibernate/annotations/CreationTimestamp.html
    @CreationTimestamp
    @Column
    private Date exec_timestamp;

    @Column
    private Float response_time;

    @Column
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

    public Long getTsuImplId() {
        return tsuImplId;
    }

    public void setTsuImplId(Long tsuImplId) {
        this.tsuImplId = tsuImplId;
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
