package jnetgraph.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserPeriodRequestDTO {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date from;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date to;

    private Long userId;

    public UserPeriodRequestDTO() {
    }

    public UserPeriodRequestDTO(Date from, Date to, Long userId) {
        this.from = from;
        this.to = to;
        this.userId = userId;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
