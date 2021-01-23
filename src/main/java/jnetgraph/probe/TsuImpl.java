package jnetgraph.probe;

public class TsuImpl extends NetDataGatherer {


    boolean measureAll() {
        return false;
    }

    boolean measureLatency() {
        return false;
    }

    @Override
    boolean measureDownload() {
        return false;
    }

    @Override
    boolean measureUpload() {
        return false;
    }

    @Override
    float getAverageDownload() {
        return 0;
    }

    @Override
    float getAverageUpload() {
        return 0;
    }
}
