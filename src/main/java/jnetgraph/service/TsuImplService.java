package jnetgraph.service;

import jnetgraph.model.TsuImpl;
import jnetgraph.probe.NetDataGatherer;
import jnetgraph.probe.TsuImplProbe;
import jnetgraph.repository.TsuImplRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TsuImplService {

    private boolean measuringRunning = false;
    private long interval = 5000;
    private final TsuImplRepository tsuImplRepository;

    @Autowired
    public TsuImplService(TsuImplRepository tsuImplRepository, TsuImplProbe tsuImplProbe) {
        this.tsuImplRepository = tsuImplRepository;
    }

    // Run the measuring and saving of data in a thread.
    public void runMeasurer() {
        new Thread( () -> {
            while (measuringRunning) {
                NetDataGatherer probe = new TsuImplProbe();
                probe.measureAll();
                // Test what happens when you pass NULL vs empty.
                TsuImpl impl = new TsuImpl(
                        probe.getAvgMs(),
                        probe.getAvgDl()
                );
                tsuImplRepository.save(impl);
                try {
                    System.out.println("Sleeping...");
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // Investigate difference between Thread.run() and Thread.start().
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
}
