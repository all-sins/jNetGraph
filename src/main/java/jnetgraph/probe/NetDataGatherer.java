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
    abstract void measureAll();
    public abstract void measureLatency(int precision, int threshold);
    abstract void measureDownload();
    abstract void measureUpload();

    // TODO:
    // getJitter?
    // getPacketLoss?
    // getOverAllStatus?
}
