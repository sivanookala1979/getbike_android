package com.vave.getbike.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sivanookala on 19/01/17.
 */

public class GetBikeUtils {

    public static <T> List<T> trimList(List<T> input, int size) {
        List<T> result = new ArrayList<>();
        int factor = input.size() / size;
        for (int i = 0; i < input.size(); i++) {
            if (factor == 0 || i % factor == 0) {
                result.add(input.get(i));
            }
        }
        return result;
    }

    public static <T> List<T> createList(T... input) {
        List<T> result = new ArrayList<>();
        for (T item : input) {
            result.add(item);
        }
        return result;
    }
}
