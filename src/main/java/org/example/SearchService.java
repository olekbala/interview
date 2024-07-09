package org.example;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private List<String> data;

    public void init(int size) {
        data = new ArrayList<>(size);

        for (var i = 0; i < size; i++) {
            data.add(getRandomString());
        }
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
        throw new NotImplementedException();
    }

    public String getString(int index) {
        return data.get(index);
    }
}
