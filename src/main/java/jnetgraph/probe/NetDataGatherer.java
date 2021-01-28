package jnetgraph.probe;

import java.io.IOException;
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
<<<<<<< HEAD
    float minUp = 0;
    float maxUp = 0;
    float avgUp = 0;

    // Used to populate whole object with all data,
    // or specific parts.
    abstract boolean measureAll() throws IOException;
    abstract boolean measureLatency();
    abstract boolean measureDownload();
    abstract boolean measureUpload();
=======
    protected float minUp = 0;
    protected float maxUp = 0;
    protected float avgUp = 0;
>>>>>>> 011c6cd0853668c2dadf5c265efadafef24a0dd8

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
