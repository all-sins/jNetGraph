package jnetgraph.service;

import jnetgraph.model.TsuImpl;
import jnetgraph.model.User;
import jnetgraph.probe.NetDataGatherer;
import jnetgraph.probe.TsuImplProbe;
import jnetgraph.repository.TsuImplRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class TsuImplService {

    private boolean measuringRunning = false;
    private long interval = 60000L;
    private long userIdToMeasure;
    private final TsuImplRepository tsuImplRepository;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(TsuImplService.class);

    @Autowired
    public TsuImplService(TsuImplRepository tsuImplRepository, TsuImplProbe tsuImplProbe) {
        this.tsuImplRepository = tsuImplRepository;
    }

    public List<TsuImpl> getDataForPeriod(String strFrom, String strTo, Long userId) throws ParseException {

        // Formatter that parses to the specific format used in the database.
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // Parse incoming strings from API to java.util.Date objects.
        Date from = formatter.parse(strFrom);
        Date to = formatter.parse(strTo);

        // Create Date of right now.
        Date now = new Date();

        // Get java.util.Calendar for incoming Strings that were parsed to java.util.Date's.
        Calendar fromCal = GregorianCalendar.getInstance();
        fromCal.setTime(from);
        Calendar toCal = GregorianCalendar.getInstance();
        toCal.setTime(to);

        // Get java.util.Calendar for current java.util.Date.
        Calendar nowCal = GregorianCalendar.getInstance();
        nowCal.setTime(now);

        // Copy over hours, minutes, seconds and milliseconds from NOW Date to the in Date.
        // This is done so that the user does not have to enter HH:mm:ss:SSS and can just
        // use yyyy-MM-dd.
        fromCal.set(Calendar.HOUR_OF_DAY, nowCal.get(Calendar.HOUR_OF_DAY));
        fromCal.set(Calendar.MINUTE, nowCal.get(Calendar.MINUTE));
        fromCal.set(Calendar.SECOND, nowCal.get(Calendar.SECOND));
        fromCal.set(Calendar.MILLISECOND, nowCal.get(Calendar.MILLISECOND));

        // Set the hours, minutes, seconds and milliseconds to a second before the next day,
        // because when a user asks until that day, they mean - including that day.
        toCal.set(Calendar.HOUR_OF_DAY, 23);
        toCal.set(Calendar.MINUTE, 59);
        toCal.set(Calendar.SECOND, 59);
        toCal.set(Calendar.MILLISECOND, 999);

        return tsuImplRepository.getDataForPeriod(fromCal.getTime(), toCal.getTime(), userId);
    }

    // Run the measuring and saving of data in a thread.
    public void runMeasurer(User user) {
        log.debug("[TsuImpl] Sleep interval set to: "+ interval +" ms ("+ interval / 1000 +" sec)");
        new Thread( () -> {
            while (measuringRunning) {
                log.debug("[TsuImpl] Measuring for USER ID ["+ user.getId() +"]");
                NetDataGatherer probe = new TsuImplProbe();
                long temp = System.currentTimeMillis();
                probe.measureAll();
                log.debug("[TsuImpl] Measurement completed in "+ (System.currentTimeMillis() - temp) +" ms ("+ (System.currentTimeMillis() - temp) / 1000 +" sec)");
                TsuImpl impl = new TsuImpl(
                        user,
                        probe.getAvgMs(),
                        probe.getAvgDl()
                );
                tsuImplRepository.save(impl);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isMeasuringRunning() {
        return measuringRunning;
    }

    public void setMeasuringRunning(boolean measuringRunning) {
        this.measuringRunning = measuringRunning;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public long getUserIdToMeasure() {
        return userIdToMeasure;
    }

    public void setUserIdToMeasure(long userIdToMeasure) {
        this.userIdToMeasure = userIdToMeasure;
    }

}
