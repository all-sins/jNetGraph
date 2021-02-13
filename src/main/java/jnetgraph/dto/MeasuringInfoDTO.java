package jnetgraph.dto;

import jnetgraph.service.TsuImplService;

public class MeasuringInfoDTO {

    private String running;
    private Long userId;
    private Long interval = 60000L;;
    private String details;



    public MeasuringInfoDTO() {
    }

    public MeasuringInfoDTO(String running, String details) {
        this.running = running;
        this.details = details;
    }

    public MeasuringInfoDTO(String running, String details, Long interval) {
        this.running = running;
        this.details = details;
        this.interval = interval;
    }

    public MeasuringInfoDTO(String running, String details, Long interval, Long userId) {
        this.running = running;
        this.userId = userId;
        this.interval = interval;
        this.details = details;
    }

    public String getRunning() {
        return running;
    }

    public void setRunning(String running) {
        this.running = running;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
