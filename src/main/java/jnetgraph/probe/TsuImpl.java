package jnetgraph.probe;

public class TsuImpl extends NetDataGatherer {

    @Override
    boolean measureAll() {
        return false;
    }

    @Override
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
