package jnetgraph.probe;

import jnetgraph.exception.MeasuringException;

import org.slf4j.LoggerFactory;
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

    private final org.slf4j.Logger log = LoggerFactory.getLogger(TsuImplProbe.class);

    @Override
    public void measureAll() {
        log.debug("[TsuImpl] Starting latency test...");
        measureLatency(30, 10000);
        log.debug("[TsuImpl] "+ avgMs +" ms");

        log.debug("[TsuImpl] Starting download test...");
        measureDownload();
        log.debug("[TsuImpl] "+ avgDl +" kb/s");
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

        // Measure time taken to download a 5MB iso file x times.
        int x = 2;
        float fileSizeInBytes = 1 * 1024f;
        long[] dlTimes = new long[x];
        String testDomain = "http://ipv4.scaleway.testdebit.info/1M.iso";
        // Left the loop in for ease of change later if need be.
        for (int i = 0; i < x; i++) {
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
            speeds[i] = fileSizeInBytes / ( (float) dlTimes[i] / 1000f );
        }

        // Calculate average, minimum, maximum and format
        // results to a decimal precision of two digits.
        float[] res = calcAvgMinMax(speeds);
        avgDl = res[0];
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

}
