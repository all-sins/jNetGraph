package jnetgraph.dto;

public class MeasuringInfoDTO {

    private String running;
    private String details;

    public MeasuringInfoDTO() {
    }

    public MeasuringInfoDTO(String running, String details) {
        this.running = running;
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

}
