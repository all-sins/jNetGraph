package jnetgraph.mapper;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StringToDate {

    public Date convert(String dateInput) throws ParseException {
        String dateString = dateInput;
        Date date =new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return date;

    }
}
