package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;


public class SearchService {

    private List<String> data;
    private List<Integer> hashCodes;

    public void init(int size) throws InterruptedException {
        data = Collections.synchronizedList(new ArrayList<>(size));
        hashCodes = Collections.synchronizedList(new ArrayList<>(size));

        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) {
            int start = i == 0 ? 0 : i * (size / 5) + 1;
            int end = Math.min((i + 1) * (size / 5), size - 1);
            Thread thread = new Thread(() -> {
                for (int j = start; j <= end; ++j) {
                    String str = getRandomString();
                    data.add(str);
                    hashCodes.add(str.hashCode());
                }
                countDownLatch.countDown();
            });
            thread.start();


        }
        countDownLatch.await();
    }

    public String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(16, 257);
    }

    /**
     *
     * @param source
     * @return index >= 0, if <src>source</src> is found; otherwise -1
     */
    public int findString(String source) {
        int sourceHash = source.hashCode();
        for (int i = 0; i < data.size(); ++i) {
            if (hashCodes.get(i) == sourceHash && StringUtils.equals(data.get(i), source)) {
                return i;
            }
        }
        return -1;
    }

    public int findString2(String source) throws InterruptedException {


        int sourceHash = source.hashCode();
        AtomicInteger result = new AtomicInteger(-1);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) {
            int start = i == 0 ? 0 : i * (data.size() / 5) + 1;
            int end = Math.min((i + 1) * (data.size() / 5), data.size() - 1);
            Thread thread = new Thread(() -> {
                for (int j = start; j <= end; ++j) {
                    if (hashCodes.get(j) == sourceHash && StringUtils.equals(data.get(j), source)) {
                        result.set(j);
                        countDownLatch.countDown();
                        return;
                    }
                }
                countDownLatch.countDown();
            });
            thread.start();


        }
        countDownLatch.await();
        return result.get();
    }


    public String getString(int index) {
        return data.get(index);
    }
}
