package jnetgraph.probe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class NetDataGatherer {

    // Variables for latency.
    protected float minMs = 0;
    protected float maxMs = 0;
    protected float avgMs = 0;

    // Variables for download speed.
    protected float minDl = 0;
    protected float maxDl = 0;
    protected float avgDl = 0;

    // Variables for upload speed.
    protected float minUp = 0;
    protected float maxUp = 0;
    protected float avgUp = 0;

    // Used to populate whole object with all data, or specific parts.
    abstract public void measureAll();
    abstract public void measureLatency(int precision, int threshold);
    abstract public void measureDownload();

    // TODO:
    // getJitter?
    // getPacketLoss?
    // getOverAllStatus?


    public float getMinMs() {
        return minMs;
    }

    public float getMaxMs() {
        return maxMs;
    }

    public float getAvgMs() {
        return avgMs;
    }

    public float getMinDl() {
        return minDl;
    }

    public float getMaxDl() {
        return maxDl;
    }

    public float getAvgDl() {
        return avgDl;
    }

    public float getMinUp() {
        return minUp;
    }

    public float getMaxUp() {
        return maxUp;
    }

    public float getAvgUp() {
        return avgUp;
    }
}
