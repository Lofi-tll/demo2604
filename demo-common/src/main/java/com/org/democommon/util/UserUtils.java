package com.org.democommon.util;

import java.time.LocalDateTime;

public class UserUtils {
    public static String generateStudentId() {
        String year = String.valueOf(LocalDateTime.now().getYear());
        String suffix = String.format("%04d", System.nanoTime() % 10000);

        return year + "00" + suffix;
    }
}
