package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * Data search
 *
 */
@Slf4j
public class Application {

    public static int DATA_SIZE = 10_000_000;

    public static void main(String[] args) {
        log.info("Loading data");

        final var searchEngine = new SearchService();
        searchEngine.init(DATA_SIZE);
        log.info("Data is ready");

        final var middle = searchEngine.getString(DATA_SIZE / 2);
        log.info("Searching middle item; {}", middle);
        final var middle_pos = searchEngine.findString(middle);
        log.info("Found middle item: {} at {}", middle, middle_pos);

        final var end = searchEngine.getString(DATA_SIZE - 1);
        log.info("Searching end item; {}", end);
        final var end_pos = searchEngine.findString(end);
        log.info("Found end item: {} at {}", end, end_pos);

        final var random = searchEngine.getRandomString();
        log.info("Searching random item; {}", random);
        final var random_pos = searchEngine.findString(random);
        log.info("Found random item: {} at {}", random, random_pos);
    }
}
