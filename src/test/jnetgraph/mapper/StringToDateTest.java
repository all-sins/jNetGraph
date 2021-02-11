package jnetgraph.mapper;

import org.junit.Test;

import java.text.ParseException;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StringToDateTest {

    StringToDate stringToDate = new StringToDate();

    @Test
    public void convert() throws ParseException {
        String stringInput = "2021-02-10 20:17:18.108";
        Calendar myCalendar = new GregorianCalendar(2021, Calendar.FEBRUARY, 10);
        Date expectedDate = myCalendar.getTime();
        Date result = stringToDate.convert(stringInput);

        assertEquals(expectedDate,result);
    }


}
