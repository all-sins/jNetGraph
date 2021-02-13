package jnetgraph.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TsuImplDTO {

    // Test with and without this. Supposedly it returns some weird
    // number without this black magic annotation.
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date exec_timestamp;
    private Float response_time;
    private Float download_speed;

    public TsuImplDTO(Date exec_timestamp, Float response_time, Float download_speed) {
        this.exec_timestamp = exec_timestamp;
        this.response_time = response_time;
        this.download_speed = download_speed;
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
}
