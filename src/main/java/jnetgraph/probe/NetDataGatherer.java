package jnetgraph.probe;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public abstract class NetDataGatherer {

    // Used to store measured data.
    ArrayList<Float> msList = new ArrayList<>();
    ArrayList<Float> dlList = new ArrayList<>();
    ArrayList<Float> upList = new ArrayList<>();

    // Variables for latency.
    float minMs = 0;
    float maxMs = 0;
    float avgMs = 0;

    // Variables for download speed.
    float minDl = 0;
    float maxDl = 0;
    float avgDl = 0;

    // Variables for upload speed.
    float minUp = 0;
    float maxUp = 0;
    float avgUp = 0;

    // Used to populate whole object with all data,
    // or specific parts.
    abstract boolean measureAll();
    abstract boolean measureLatency();
    abstract boolean measureDownload();
    abstract boolean measureUpload();

    // General methods for calculating statistics.
    float getAverageLatency() {
        Optional<Float> optionalFloat = msList.stream().max(Comparator.comparing(Float::valueOf));
        if (optionalFloat.isPresent()) {
            // Leaving the unboxing for now.
            // TL:DR
            // int      ->  Integer (Autoboxing)
            // Integer  ->  int     (Unboxing)
            return optionalFloat.get().floatValue();
        }
        throw new NullPointerException("No value found from stream of floats!");
    }

    abstract float getAverageDownload();
    abstract float getAverageUpload();
    // TODO:
    // getJitter?
    // getPacketLoss?
    // getOverAllStatus?
}
