package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


/**
 * Data search
 *
 */
@Slf4j
public class Application {

    public static int DATA_SIZE = 10_000_000;

    private static SearchService searchEngine = new SearchService();

    public static void main(String[] args) throws InterruptedException {
        log.info("Loading data");

        searchEngine.init(DATA_SIZE);
        log.info("Data is ready");

        final var middle = searchEngine.getString(DATA_SIZE / 2);
        findString(middle, 5);

        final var end = searchEngine.getString(DATA_SIZE - 1);
        findString(end, 3);

        final var random = searchEngine.getRandomString();
        findString(random, 3);
    }

    private static void findString(String str, int nSamples) throws InterruptedException {
        log.info("Searching item; {}", str);
        List<Long> times = new ArrayList<>();
        for (int i = 0; i < nSamples; ++i) {
            long startMilisecs = System.currentTimeMillis();
            final var pos = searchEngine.findString2(str);
            long timeMilis = System.currentTimeMillis() - startMilisecs;
            times.add(timeMilis);
            log.info("Found item: {} at {}; time of search: {}", str, pos, timeMilis);
        }
        long minTime =  times.stream().min(Long::compareTo).get();
        long maxTime =  times.stream().max(Long::compareTo).get();
        double avgTime =  times.stream().reduce(0L, Long::sum) * 1.0 / times.size();
        log.info("Minimum time: {}, max time: {}, avg time: {}", minTime, maxTime, avgTime);
    }
}
