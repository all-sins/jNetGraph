package jnetgraph.probe;

import jnetgraph.exception.MeasuringException;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

@Component
public class TsuImplProbe extends NetDataGatherer {

    @Override
    public void measureAll() {
        measureLatency(10, 10000);
        measureDownload();
        // measureUpload();
    }

    @Override
    public void measureLatency(int precision, int threshold) {
        // byte precision = 10;
        long startTime;
        // int threshold = 10000;
        long[] respTimes = new long[precision];

        // Measure response times from <hostname>, <precision> amount
        // of times with a timeout threshold of <threshold>.
        try {
            InetAddress address = InetAddress.getByName("8.8.8.8");
            for (int i = 0; i < respTimes.length; i++) {
                startTime = System.currentTimeMillis();
                boolean success = address.isReachable(threshold);
                if (success) {
                    respTimes[i] = System.currentTimeMillis() - startTime;
                } else {
                    respTimes[i] = 10000;
                }
            }
        } catch (UnknownHostException e) {
            throw new MeasuringException("101", "Could not resolve address used to test latency against.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MeasuringException("100", "Latency measuring failed catastrophically!");
        }

        // Calculate average from measured results.
        float[] floatRespTimes = new float[respTimes.length];
        for (int i = 0; i < respTimes.length; i++) {
            floatRespTimes[i] = (float) respTimes[i];
        }
        float[] res = calcAvgMinMax(floatRespTimes);
        avgMs = res[0];
        minMs = res[1];
        maxMs = res[2];
    }

    @Override
    public void measureDownload() {

        // TODO:
        // Switch this to a cleaner implementation that measures the
        // time it takes to load the data from the URL into MEMORY
        // not write it to a file. Avoid ambiguous disk I/O bottleneck.

        // Measure time taken to download a 1MB file 5 times.
        long[] dlTimes = new long[5];
        String testDomain = "http://ipv4.ikoula.testdebit.info/1M.iso";
        for (int i = 0; i < 5; i++) {
            // This one-liner is pretty nuts. It does all of the following:
            // 1. Create a URL object from a string in the parameter of the constructor.
            // 2. Call the openStream() method which returns a InputStream from a URL object.
            // 3. Use that InputStream as a parameter to the constructor of BufferedInputStream
            // to create a BufferedInputStream object.
            try (BufferedInputStream in = new BufferedInputStream(new URL(testDomain).openStream())) {
                long start = System.currentTimeMillis();
                in.readAllBytes();
                dlTimes[i] = System.currentTimeMillis() - start;
            } catch (MalformedURLException e) {
                throw new MeasuringException("201", "Failed to resolve given host! Invalid URL!");
            } catch (IOException e) {
                e.printStackTrace();
                throw new MeasuringException("200", "Failed to measure latency against given host!");
            }
        }

        // Calculate average download speed.
        float speedSum = 0;
        float[] speeds = new float[dlTimes.length];

        // Convert array of long millisecond download times
        // to an array of float KB/s.
        for (int i = 0; i < dlTimes.length; i++) {
            speeds[i] = 1024f / ( (float) dlTimes[i] / 1000f );
        }

        // Calculate average, minimum, maximum and format
        // results to a decimal precision of two digits.
        float[] res = calcAvgMinMax(speeds);
        avgDl = res[0];
        minDl = res[1];
        maxDl = res[2];
    }

    @Override
    public void measureUpload() {
        // TODO: Implement or scrap this method.
        // I can't think of a way to implement this.
        // For now.
    }

    private float[] calcAvgMinMax(float[] array) {
        float sum = 0;
        float max = 0;
        float min = Float.MAX_VALUE;
        for (float arrayItem : array) {
            if (arrayItem > max) {max = arrayItem;}
            if (arrayItem < min) {min = arrayItem;}
            sum += arrayItem;
        }
        float avg = BigDecimal.valueOf(sum / array.length).setScale(2, RoundingMode.UP).floatValue();
        max = BigDecimal.valueOf(max).setScale(2, RoundingMode.UP).floatValue();
        min = BigDecimal.valueOf(min).setScale(2, RoundingMode.UP).floatValue();
        return new float[]{avg, min, max};
    }

    // Used for debugging, remove once finished.
    private void printDebugInfo() {
        System.out.println("\t\t\tMIN\tAVG\tMAX");
        System.out.println("Latency:\t" + minMs + "\t" + avgMs + "\t" + maxMs);
        System.out.println("Download:\t" + minDl + "\t" + avgDl + "\t" + maxDl);
        System.out.println("Upload:\t" + minUp + "\t" + avgUp + "\t" + maxUp);
    }

    // Used for debugging, remove once finished.
    public static void main(String[] args) {
        TsuImplProbe tsuImplProbe = new TsuImplProbe();
        tsuImplProbe.printDebugInfo();
        tsuImplProbe.measureAll();
        tsuImplProbe.printDebugInfo();
    }
}
