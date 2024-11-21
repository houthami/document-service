package org.dms.document.utils;

import java.security.SecureRandom;
import java.util.Date;

public class FileUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateReferenceFile(String fileName, String contentType) {
        Date date = new Date();
        String reference = date.getTime() + generateRandomString(fileName, LENGTH);
        return reference+"." + fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static String generateRandomString(String s, int length) {
        StringBuilder sb = new StringBuilder(length);
        String sbChar = s + CHARACTERS;
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(sbChar.length());
            sb.append(sbChar.charAt(index));
        }
        sb = new StringBuilder(sb.toString().replaceAll("\\s+", ""));
        return sb.toString();
    }
}
